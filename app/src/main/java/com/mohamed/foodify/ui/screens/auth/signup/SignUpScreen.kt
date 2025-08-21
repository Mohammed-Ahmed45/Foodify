package com.mohamed.foodify.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.screens.splash.SplashScreen
import com.mohamed.foodify.ui.utills.AuthDialog
import com.mohamed.foodify.ui.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel= hiltViewModel(),
    navController: NavController? = null
) {
    val scrollState = rememberScrollState()

    if (authViewModel.isLoading){
        AuthDialog()
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
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Form Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.name,
                onValueChange = {
                    authViewModel.name =it
                                },
                label = { Text("Full Name") },
                singleLine = true,
                isError = authViewModel.nameError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                supportingText = {
                    authViewModel.nameError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.email,
                onValueChange = {
                    authViewModel.email =it
                                },
                label = { Text("Email") },
                singleLine = true,
                isError = authViewModel.emailError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                supportingText = {
                    authViewModel.emailError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.password,
                onValueChange = {
                    authViewModel.password =it
                                },
                label = { Text("Password") },
                singleLine = true,
                isError = authViewModel.passwordError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                supportingText = {
                    authViewModel.passwordError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = {
                authViewModel.signUp(
                    onSuccess = {
                        navController?.navigate(Route.MAIN_SCREEN){
                            popUpTo(Route.SIGNUP){inclusive=true}
                        }
                    },
                    onFail = {

                    }
                )
                navController?.navigate(Route.HOME_SCREEN)

            }

//            authViewModel.signUp(
//                onSuccess = {
//                    navController?.navigate(Route.MAIN_SCREEN) {
//                        popUpTo(Route.LOGIN_SCREEN) { inclusive = true }
//                    }
//                },
//                onFail = {
//                    // Registration failed, error is already shown via registrationError
//                }
//            )
//
//        },
            ,colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                disabledContainerColor = Color.Blue.copy(alpha = 0.6f)
            ),
            enabled = !authViewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ){
            if (authViewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    SignUpScreen()
}