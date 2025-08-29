package com.mohamed.domain.usecase.orders

import com.google.firebase.Timestamp
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.repositories.orders.OrdersRepo
import javax.inject.Inject

class OrdersUseCase @Inject constructor(
    private val ordersRepo: OrdersRepo,
) {
    suspend fun userPlaceOrder(
        id: String,
        userId: String,
        address: String,
        cartItems: Map<String, Long>,
        date: Timestamp,
        status: String,
    ): OrdersEntity {
        return ordersRepo.placeOrder(id, userId, address, cartItems, date, status)
    }

    suspend fun getUsersOrders(userId: String): List<OrdersEntity> {
        return ordersRepo.getUserOrders(userId)
    }
}