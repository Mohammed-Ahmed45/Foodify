package com.mohamed.data.model

import com.google.firebase.Timestamp
import com.mohamed.domain.model.orders.OrdersEntity

data class OrdersDto(
    val id: String = "",
    val userId: String = "",
    val address: String = "",
    val date: Timestamp = Timestamp.now(),
    val status: String = "",
    val cartItems: Map<String, Long>? = mapOf(),
) {
    fun toOrderEntity(): OrdersEntity {
        return OrdersEntity(
            id = id,
            userId = userId,
            address = address,
            date = date,
            status = status,
            cartItems = cartItems
        )
    }
}