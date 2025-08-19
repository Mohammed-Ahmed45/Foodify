package com.mohamed.data.repo.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.UserModelDto
import com.mohamed.domain.model.auth.User
import com.mohamed.domain.repositories.auth.AuthRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepo {

    override suspend fun signUp(name: String, email: String, password: String): User {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID is null")

        val dto = UserModelDto(userId, name, email)
        firestore.collection("users").document(userId).set(dto).await()

        return dto.toDomain()
    }

    override suspend fun signIn(email: String, password: String): User {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID is null")

        val snapshot = firestore.collection("users").document(userId).get().await()
        val dto = snapshot.toObject(UserModelDto::class.java) ?: throw Exception("User not found")

        return dto.toDomain()
    }
}
