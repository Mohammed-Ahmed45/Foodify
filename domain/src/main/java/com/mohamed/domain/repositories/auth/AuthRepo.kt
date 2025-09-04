package com.mohamed.domain.repositories.auth

import com.mohamed.domain.model.auth.User

interface AuthRepo {
    suspend fun signUp(
        name: String,
        email: String,
        phoneNumber: String,
        address: String,
        password: String,
        isAdmin: Boolean,
    ): User

    suspend fun signIn(email: String, password: String, isAdmin: Boolean): User
}