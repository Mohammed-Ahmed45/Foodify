package com.mohamed.domain.repositories.orders

import com.google.firebase.Timestamp
import com.mohamed.domain.model.orders.OrdersEntity
import kotlinx.coroutines.flow.Flow

interface OrdersRepo {
    suspend fun placeOrder(
        id: String,
        userId: String,
        name: String,
        address: String,
        cartItems: Map<String, Long>,
        date: Timestamp,
        status: String,
    ): OrdersEntity

    fun getUserOrders(userId: String): Flow<List<OrdersEntity>>

}