package com.mohamed.domain.model.auth


data class User(
    val userId: String,
    val name: String,
    val email: String,
    val cartItems: Map<String, Long>? = mapOf()
)