package com.mohamed.data.repo.order

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.OrdersDto
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.repositories.orders.OrdersRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : OrdersRepo {


    override suspend fun placeOrder(
        id: String,
        userId: String,
        name: String,
        address: String,
        cartItems: Map<String, Long>,
        date: Timestamp,
        status: String,
    ): OrdersEntity {
        val dto = OrdersDto(
            id = id,
            userId = userId,
            name = name,
            address = address,
            date = date,
            status = status,
            cartItems = cartItems
        )
        firebaseFirestore.collection("orders")
            .document(id)
            .set(dto)
            .await()
        return dto.toOrderEntity()
    }

    override fun getUserOrders(userId: String): Flow<List<OrdersEntity>> = callbackFlow {
        val listener = firebaseFirestore.collection("orders")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    cancel("Firestore error", e)
                    return@addSnapshotListener
                }
                val order = snapshot?.documents?.mapNotNull {
            it.toObject(OrdersDto::class.java)?.toOrderEntity()
                } ?: emptyList()
                trySend(order) // success
            }
        awaitClose { listener.remove() }

    }
}
