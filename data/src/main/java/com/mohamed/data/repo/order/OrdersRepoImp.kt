package com.mohamed.data.repo.order

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.OrdersDto
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.orders.OrdersRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : OrdersRepo {


    override suspend fun placeOrder(
        id: String,
        userId: String,
        address: String,
        cartItems: Map<String, Long>,
        date: Timestamp,
        status: String,
    ): OrdersEntity {
        val dto = OrdersDto(
            id = id,
            userId = userId,
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

    override suspend fun getUserOrders(userId: String): List<OrdersEntity> {
        val snapshot = firebaseFirestore.collection("orders")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        return snapshot.documents.mapNotNull {
            it.toObject(OrdersDto::class.java)?.toOrderEntity()
        }

    }
}