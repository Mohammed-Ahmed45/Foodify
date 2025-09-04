package com.mohamed.domain.model.orders

import com.google.firebase.Timestamp

data class OrdersEntity(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val address: String = "",
    val date: Timestamp = Timestamp.now(),
    val status: String = "",
    val cartItems: Map<String, Long>? = mapOf(),
)
