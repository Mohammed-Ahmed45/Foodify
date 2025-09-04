package com.mohamed.domain.model.auth


data class User(
    val userId: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val isAdmin: Boolean,
    val cartItems: Map<String, Long>? = mapOf(),
)