package com.mohamed.data.model

import com.mohamed.domain.model.auth.User

// data/model/UserModelDto.kt
data class UserModelDto(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val cartItems: Map<String, Long>? = mapOf()
) {
    fun toDomain() = User(userId, name, email,cartItems)
}

fun User.toDto() = UserModelDto(userId, name, email,cartItems)
