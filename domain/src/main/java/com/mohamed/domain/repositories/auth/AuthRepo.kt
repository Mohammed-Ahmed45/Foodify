package com.mohamed.domain.repositories.auth

import com.mohamed.domain.model.auth.User

interface AuthRepo {
    suspend fun signUp(name: String, email: String, password: String): User
    suspend fun signIn(email: String, password: String): User
}