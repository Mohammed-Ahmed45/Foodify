package com.mohamed.domain.repositories.orders

import com.google.firebase.Timestamp
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.model.products.Products
import java.util.Date

interface OrdersRepo {
    suspend fun placeOrder(
        id: String,
        userId: String,
        address: String,
        cartItems: Map<String, Long>,
        date: Timestamp,
        status: String,
    ): OrdersEntity

    suspend fun getUserOrders(userId: String): List<OrdersEntity>

}