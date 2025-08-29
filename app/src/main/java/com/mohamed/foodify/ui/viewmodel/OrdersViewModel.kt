package com.mohamed.foodify.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.usecase.orders.OrdersUseCase
import com.mohamed.foodify.ui.error.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersUseCase: OrdersUseCase,
    private val auth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : ViewModel() {

    var showAnimation by mutableStateOf(false)
    var isOrderSubmitting by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    val orderStatusList = mutableStateListOf<OrdersEntity>()


    val totalOrdersCount: Int
        get() = orderStatusList.size

    val orderedCount: Int
        get() = orderStatusList.count { it.status == "ORDERED" }

    val inProgressCount: Int
        get() = orderStatusList.count { it.status == "IN_PROGRESS" }

    val deliveredCount: Int
        get() = orderStatusList.count { it.status == "DELIVERED" }

    val cancelledCount: Int
        get() = orderStatusList.count { it.status == "CANCELLED" }

    fun sendUserOrdes() {
        if (isOrderSubmitting) return

        viewModelScope.launch {
            try {
                isOrderSubmitting = true
                val userId = auth.currentUser?.uid.toString()
                val userRef = firebaseFirestore.collection("users")
                    .document(userId)

                val userDoc = userRef.get().await()
                val cartItems = userDoc.get("cartItems") as? Map<String, Long> ?: emptyMap()

                ordersUseCase.userPlaceOrder(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    address = userDoc.get("address").toString(),
                    cartItems = cartItems,
                    date = Timestamp.now(),
                    status = "ORDERED"
                )

                userRef.update("cartItems", FieldValue.delete()).await()
                refreshOrders()

            } catch (e: Exception) {
                handleError(e)
                showAnimation = false
            } finally {
                isOrderSubmitting = false
            }
        }
    }

    fun getUserOrder() {
        viewModelScope.launch {
            try {
                isLoading = true
                val userId = auth.currentUser?.uid.toString()
                val response = ordersUseCase.getUsersOrders(userId)
                orderStatusList.clear()
                orderStatusList.addAll(response)
                Log.e("TAG", "getUserOrder: $response")
            } catch (e: Exception) {
                handleError(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun refreshOrders() {
        getUserOrder()
    }
}