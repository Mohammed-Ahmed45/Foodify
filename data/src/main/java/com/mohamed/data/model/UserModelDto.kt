package com.mohamed.data.model

import com.mohamed.domain.model.auth.User

// data/model/UserModelDto.kt
data class UserModelDto(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    @field:JvmField
    val admin: Boolean = false,
    val cartItems: Map<String, Long>? = mapOf(),
) {
    fun toDomain() = User(userId, name, email, phoneNumber, address, admin, cartItems)
}

fun User.toDto() = UserModelDto(userId, name, email, phoneNumber, address, isAdmin, cartItems)
