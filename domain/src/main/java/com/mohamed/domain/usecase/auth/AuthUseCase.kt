package com.mohamed.domain.usecase.auth

import com.mohamed.domain.model.auth.User
import com.mohamed.domain.repositories.auth.AuthRepo
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repo: AuthRepo
) {
    suspend  fun signUp(name: String, email: String, password: String): User {
        return repo.signUp(name, email, password)
    }
    suspend  fun signIn(email: String, password: String): User {
        return repo.signIn(email, password)
    }
}


