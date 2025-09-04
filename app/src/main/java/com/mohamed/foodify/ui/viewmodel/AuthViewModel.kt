package com.mohamed.foodify.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.UserModelDto
import com.mohamed.domain.model.auth.User
import com.mohamed.domain.usecase.auth.AuthUseCase
import com.mohamed.foodify.ui.error.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val authFirebase: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): ViewModel() {

    // Form Fields
    var name by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var address by mutableStateOf("")
    var isAdmin by mutableStateOf(false)
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Error States
    var nameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var phoneNumberError by mutableStateOf<String?>(null)
    var addressError by mutableStateOf<String?>(null)

    // Current User & Loading States
    var currentUser by mutableStateOf<User?>(null)
    var isLoading by mutableStateOf(false)
    var isRetrying by mutableStateOf(false)
    var retryCount by mutableStateOf(0)

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val NETWORK_TIMEOUT = 30000L // 30 seconds
        private const val RETRY_DELAY = 2000L // 2 seconds
    }

    // Network connectivity check
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    )
        } else {
            @Suppress("DEPRECATION")
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnected
        }
    }

    // Validation Functions
    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            name.length < 2 -> "Name must be at least 2 characters"
            name.length > 50 -> "Name is too long"
            !name.matches(Regex("^[a-zA-Z\\s\u0600-\u06FF]+$")) -> "Name should contain only letters"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        val emailPattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        )
        return when {
            email.isBlank() -> "Email is required"
            !emailPattern.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            password.length > 128 -> "Password is too long"
            !password.any { it.isDigit() } -> "Password must contain at least one number"
            !password.any { it.isLetter() } -> "Password must contain at least one letter"
            else -> null
        }
    }

    private fun validatePhoneNumber(phone: String): String? {
        return when {
            phone.isBlank() -> "Phone number is required"
            phone.length < 10 -> "Phone number is too short"
            phone.length > 15 -> "Phone number is too long"
            !phone.matches(Regex("^[+]?[0-9\\s-()]+$")) -> "Invalid phone number format"
            else -> null
        }
    }

    private fun validateAddress(address: String): String? {
        return when {
            address.isBlank() -> "Address is required"
            address.length < 5 -> "Address is too short"
            address.length > 200 -> "Address is too long"
            else -> null
        }
    }

    // Real-time validation functions
    fun validateNameInput(input: String) {
        name = input
        nameError = validateName(input)
    }

    fun validateEmailInput(input: String) {
        email = input
        emailError = validateEmail(input)
    }

    fun validatePasswordInput(input: String) {
        password = input
        passwordError = validatePassword(input)
    }

    fun validatePhoneNumberInput(input: String) {
        phoneNumber = input
        phoneNumberError = validatePhoneNumber(input)
    }

    fun validateAddressInput(input: String) {
        address = input
        addressError = validateAddress(input)
    }

    private fun clearErrors() {
        nameError = null
        emailError = null
        passwordError = null
        phoneNumberError = null
        addressError = null
    }

    private fun validateSignUpForm(): Boolean {
        val nameValidation = validateName(name)
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val phoneValidation = validatePhoneNumber(phoneNumber)
        val addressValidation = validateAddress(address)

        nameError = nameValidation
        emailError = emailValidation
        passwordError = passwordValidation
        phoneNumberError = phoneValidation
        addressError = addressValidation

        return listOf(
            nameValidation, emailValidation, passwordValidation,
            phoneValidation, addressValidation
        ).all { it == null }
    }

    private fun validateSignInForm(): Boolean {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)

        emailError = emailValidation
        passwordError = passwordValidation

        return emailValidation == null && passwordValidation == null
    }

    // Enhanced SignUp with network handling
    fun signUp(
        context: Context,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        if (!isNetworkAvailable(context)) {
            onFail("No internet connection. Please check your network and try again.")
            return
        }

        if (!validateSignUpForm()) {
            onFail("Please fix the errors above")
            return
        }

        performAuthOperation(
            operation = {
                authUseCase.signUp(
                    name = name.trim(),
                    email = email.trim().lowercase(),
                    password = password,
                    phoneNumber = phoneNumber.trim(),
                    address = address.trim(),
                    isAdmin = isAdmin
                )
            },
            onSuccess = { user ->
                currentUser = user
                onSuccess()
            },
            onFail = onFail,
            operationType = "Sign Up"
        )
    }

    // Enhanced SignIn with network handling and retry
    fun signIn(
        context: Context,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        if (!isNetworkAvailable(context)) {
            onFail("No internet connection. Please check your network and try again.")
            return
        }

        if (!validateSignInForm()) {
            onFail("Please fix the errors above")
            return
        }

        performAuthOperation(
            operation = {
                authUseCase.signIn(
                    email = email.trim().lowercase(),
                    password = password,
                    isAdmin = isAdmin
                )
            },
            onSuccess = { user ->
                currentUser = user
                onSuccess()
            },
            onFail = onFail,
            operationType = "Sign In"
        )
    }

    // Generic auth operation with retry mechanism
    private fun performAuthOperation(
        operation: suspend () -> User,
        onSuccess: (User) -> Unit,
        onFail: (String) -> Unit,
        operationType: String,
    ) {
        viewModelScope.launch {
            var attempt = 0
            retryCount = 0

            while (attempt < MAX_RETRY_ATTEMPTS) {
                try {
                    isLoading = true
                    isRetrying = attempt > 0
                    clearErrors()

                    if (attempt > 0) {
                        Log.i(
                            "AuthViewModel",
                            "$operationType: Attempt ${attempt + 1} of $MAX_RETRY_ATTEMPTS"
                        )
                    }

                    val user = withTimeout(NETWORK_TIMEOUT) {
                        operation()
                    }

                    // Success - reset retry count and return
                    retryCount = 0
                    isRetrying = false
                    onSuccess(user)
                    return@launch

                } catch (e: TimeoutCancellationException) {
                    Log.w("AuthViewModel", "$operationType timeout on attempt ${attempt + 1}")
                    handleRetryOrFail(
                        attempt = attempt,
                        maxAttempts = MAX_RETRY_ATTEMPTS,
                        errorMessage = "Connection timeout. Please check your internet and try again.",
                        onFail = onFail
                    )

                } catch (e: Exception) {
                    Log.d(
                        "AuthViewModel",
                        "$operationType error on attempt ${attempt + 1}: ${e.message}"
                    )

                    val isNetworkError = isNetworkRelatedError(e)

                    if (isNetworkError && attempt < MAX_RETRY_ATTEMPTS - 1) {
                        retryCount = attempt + 1
                        Log.i("AuthViewModel", "Retrying $operationType in ${RETRY_DELAY}ms...")
                        delay(RETRY_DELAY)
                    } else {
                        val errorMessage = getErrorMessage(e, isNetworkError, operationType)
                        onFail(errorMessage)
                        break
                    }
                }
                attempt++
            }

            isLoading = false
            isRetrying = false
            retryCount = 0
        }
    }

    private suspend fun handleRetryOrFail(
        attempt: Int,
        maxAttempts: Int,
        errorMessage: String,
        onFail: (String) -> Unit,
    ) {
        if (attempt < maxAttempts - 1) {
            retryCount = attempt + 1
            delay(RETRY_DELAY)
        } else {
            onFail(errorMessage)
        }
    }

    private fun isNetworkRelatedError(e: Exception): Boolean {
        val message = e.message?.lowercase() ?: ""
        return message.contains("network") ||
                message.contains("timeout") ||
                message.contains("unreachable") ||
                message.contains("connection") ||
                message.contains("host") ||
                message.contains("io exception")
    }

    private fun getErrorMessage(
        e: Exception,
        isNetworkError: Boolean,
        operationType: String,
    ): String {
        return when {
            isNetworkError -> "Network error. Please check your internet connection and try again."
            e.message?.contains("email-already-in-use") == true -> "Email is already registered"
            e.message?.contains("weak-password") == true -> "Password is too weak"
            e.message?.contains("user-not-found") == true -> "No account found with this email"
            e.message?.contains("wrong-password") == true -> "Incorrect password"
            e.message?.contains("invalid-email") == true -> "Invalid email format"
            e.message?.contains("user-disabled") == true -> "This account has been disabled"
            e.message?.contains("too-many-requests") == true -> "Too many failed attempts. Please try again later"
            else -> e.message ?: "$operationType failed. Please try again"
        }
    }

    fun loadCurrentUser() {
        val userId = authFirebase.currentUser?.uid
        if (userId == null) {
            Log.w("AuthViewModel", "No current user ID found")
            return
        }

        viewModelScope.launch {
            try {
                firebaseFirestore.collection("users")
                    .document(userId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            Log.e("AuthViewModel", "Error loading user data", error)
                            return@addSnapshotListener
                        }

                        if (snapshot != null && snapshot.exists()) {
                            val dto = snapshot.toObject(UserModelDto::class.java)
                            if (dto != null) {
                                currentUser = dto.toDomain()
                                isAdmin = dto.admin
                            } else {
                                Log.w("AuthViewModel", "Failed to parse user data")
                            }
                        } else {
                            Log.w("AuthViewModel", "User document does not exist")
                        }
                    }
            } catch (e: Exception) {
                handleError(e)
                Log.e("AuthViewModel", "Error setting up user listener", e)
            }
        }
    }

    fun resetForm() {
        name = ""
        email = ""
        password = ""
        phoneNumber = ""
        address = ""
        isAdmin = false
        clearErrors()
        retryCount = 0
        isRetrying = false
    }

    fun signOut(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                authFirebase.signOut()
                currentUser = null
                resetForm()
                onComplete()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error signing out", e)
            }
        }
    }

}