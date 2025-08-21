package com.mohamed.foodify.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mohamed.data.model.UserModelDto
import com.mohamed.domain.model.auth.User
import com.mohamed.domain.usecase.auth.AuthUseCase
import com.mohamed.foodify.ui.error.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val authFirebase: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): ViewModel() {

    var name by mutableStateOf("")
    var nameError by mutableStateOf<String?>(null)
    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    var password by mutableStateOf("")
    var passwordError by mutableStateOf<String?>(null)
    var currentUser by mutableStateOf<User?>(null)


    //    val confirmPassword = mutableStateOf("")
//    val confirmPasswordError = mutableStateOf<String?>(null)
//
    var isLoading by mutableStateOf(false)



    fun signUp(
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val user = authUseCase.signUp(name, email, password)
                currentUser = user
                onSuccess()


            } catch (e: Exception) {
                onFail(e.message ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }



    }
    fun signIn(
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {

        viewModelScope.launch {
            try {
                isLoading = true
                val user = authUseCase.signIn(email, password)
                currentUser = user
                onSuccess()
//                isLoading = true
//                authFirebase.signInWithEmailAndPassword(email, password)
//                    .await()
//                onSuccess()


            } catch (e: Exception) {
                onFail(e.message ?: "Unknown error")
                Log.d("TAG", "signIn: ${e.message}")
            } finally {
                isLoading = false
            }
        }

    }

    fun loadCurrentUser(){
        val userId=authFirebase.currentUser?.uid
        viewModelScope.launch {
            try {
                val snapshot=firebaseFirestore.collection("users")
                    .document(userId.toString())
                    .get()
                    .await()
                val dto=snapshot.toObject(UserModelDto::class.java)
                currentUser=dto?.toDomain()
            }catch (e: Exception){
                handleError(e)
            }
        }
    }
}