package com.mohamed.foodify.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.utills.AuthLoadingAnimation
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }

    if (authViewModel.isLoading) {
        AuthLoadingAnimation()
    }

    // Navigate to main screen on successful registration
    val currentUser = authViewModel.currentUser
    LaunchedEffect(currentUser) {
        currentUser?.let {
            if (it.isAdmin) {
                navController?.navigate(Route.ADMIN_SCREEN) {
                    popUpTo(Route.SIGNUP) { inclusive = true }
                }
            } else {
                navController?.navigate(Route.MAIN_SCREEN) {
                    popUpTo(Route.SIGNUP) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Title
        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Colors.DarkOrange
        )

        Text(
            text = "Sign up to get started",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp),
            color = Color.Gray
        )

        // Form Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.name,
                onValueChange = { authViewModel.validateNameInput(it) },
                label = { Text("Full Name") },
                placeholder = { Text("Enter your full name") },
                singleLine = true,
                isError = authViewModel.nameError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                supportingText = {
                    authViewModel.nameError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Colors.DarkOrange,
                    focusedIndicatorColor = Colors.DarkOrange,
                    cursorColor = Colors.DarkOrange,
                    errorCursorColor = Colors.DarkOrange,
                    errorIndicatorColor = Colors.DarkOrange,
                    errorLabelColor = Colors.DarkOrange,
                    unfocusedLabelColor = Colors.DarkOrange,
                    focusedSupportingTextColor = Colors.DarkOrange,
                    unfocusedPlaceholderColor = Colors.DarkOrange,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            // Email Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.email,
                onValueChange = { authViewModel.validateEmailInput(it) },
                label = { Text("Email") },
                placeholder = { Text("Enter your email") },
                singleLine = true,
                isError = authViewModel.emailError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                supportingText = {
                    authViewModel.emailError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Colors.DarkOrange,
                    focusedIndicatorColor = Colors.DarkOrange,
                    cursorColor = Colors.DarkOrange,
                    errorCursorColor = Colors.DarkOrange,
                    errorIndicatorColor = Colors.DarkOrange,
                    errorLabelColor = Colors.DarkOrange,
                    unfocusedLabelColor = Colors.DarkOrange,
                    focusedSupportingTextColor = Colors.DarkOrange,
                    unfocusedPlaceholderColor = Colors.DarkOrange,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            // Phone Number Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.phoneNumber,
                onValueChange = { authViewModel.validatePhoneNumberInput(it) },
                label = { Text("Phone Number") },
                placeholder = { Text("Enter your phone number") },
                singleLine = true,
                isError = authViewModel.phoneNumberError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                supportingText = {
                    authViewModel.phoneNumberError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Colors.DarkOrange,
                    focusedIndicatorColor = Colors.DarkOrange,
                    cursorColor = Colors.DarkOrange,
                    errorCursorColor = Colors.DarkOrange,
                    errorIndicatorColor = Colors.DarkOrange,
                    errorLabelColor = Colors.DarkOrange,
                    unfocusedLabelColor = Colors.DarkOrange,
                    focusedSupportingTextColor = Colors.DarkOrange,
                    unfocusedPlaceholderColor = Colors.DarkOrange,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            // Address Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.address,
                onValueChange = { authViewModel.validateAddressInput(it) },
                label = { Text("Address") },
                placeholder = { Text("Enter your address") },
                singleLine = false,
                maxLines = 2,
                isError = authViewModel.addressError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                supportingText = {
                    authViewModel.addressError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Colors.DarkOrange,
                    focusedIndicatorColor = Colors.DarkOrange,
                    cursorColor = Colors.DarkOrange,
                    errorCursorColor = Colors.DarkOrange,
                    errorIndicatorColor = Colors.DarkOrange,
                    errorLabelColor = Colors.DarkOrange,
                    unfocusedLabelColor = Colors.DarkOrange,
                    focusedSupportingTextColor = Colors.DarkOrange,
                    unfocusedPlaceholderColor = Colors.DarkOrange,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            // Password Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.password,
                onValueChange = { authViewModel.validatePasswordInput(it) },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                singleLine = true,
                isError = authViewModel.passwordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Colors.DarkOrange
                        )
                    }
                },
                supportingText = {
                    authViewModel.passwordError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Colors.DarkOrange,
                    focusedIndicatorColor = Colors.DarkOrange,
                    cursorColor = Colors.DarkOrange,
                    errorCursorColor = Colors.DarkOrange,
                    errorIndicatorColor = Colors.DarkOrange,
                    errorLabelColor = Colors.DarkOrange,
                    unfocusedLabelColor = Colors.DarkOrange,
                    focusedSupportingTextColor = Colors.DarkOrange,
                    unfocusedPlaceholderColor = Colors.DarkOrange,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Retry Status
        if (authViewModel.isRetrying) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Colors.DarkOrange,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Retrying... (${authViewModel.retryCount}/3)",
                    fontSize = 14.sp,
                    color = Colors.DarkOrange
                )
            }
        }

        // Register Button
        Button(
            onClick = {
                authViewModel.signUp(
                    context = context,
                    onSuccess = {
                        // Navigation will be handled by LaunchedEffect
                    },
                    onFail = { error ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(error)
                        }
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.DarkOrange,
                disabledContainerColor = Colors.DarkOrange.copy(alpha = 0.6f)
            ),
            enabled = !authViewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (authViewModel.isLoading && !authViewModel.isRetrying) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }

    // Snackbar Host
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    )
}