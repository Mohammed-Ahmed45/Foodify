@file:OptIn(ExperimentalMaterial3Api::class)

package com.mohamed.foodify.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mohamed.foodify.R
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Food pattern background (subtle decoration)
        FoodPatternBackground(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // App logo and delivery illustration
            DeliveryHeroSection()

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome text
            WelcomeSection()

            Spacer(modifier = Modifier.height(48.dp))

            // Auth buttons
            AuthButtonsSection(
                onSignInClick = { navController?.navigate(Route.SIGN_IN) },
                onSignUpClick = {
                    navController?.navigate(Route.SIGNUP) {
                        popUpTo(Route.SPLASH) { inclusive = true }
                    }
                },
                onGoogleSignInClick = onGoogleSignInClick
            )
        }
    }
}

@Composable
private fun FoodPatternBackground(modifier: Modifier = Modifier) {
    // Simple geometric food pattern using basic shapes
    Box(modifier = modifier) {
        repeat(15) { index ->
            val offsetX = (index * 80) % 300
            val offsetY = (index * 60) % 400

            Card(
                modifier = Modifier
                    .offset(x = offsetX.dp, y = offsetY.dp)
                    .size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Colors.Orange.copy(alpha = 0.3f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = null,
                        tint = Colors.Orange,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DeliveryHeroSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Delivery person illustration placeholder

        Box(

            contentAlignment = Alignment.Center
        ) {
            // You can replace this with your actual delivery person image
            // Image(
            //     painter = painterResource(id = R.drawable.delivery_person),
            //     contentDescription = "Delivery Person",
            //     modifier = Modifier.fillMaxSize(),
            //     contentScale = ContentScale.Crop
            // )

            // Placeholder delivery icon
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_screen),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))


}

@Composable
private fun WelcomeSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.DarkOrange,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to your account or create a new one",
            fontSize = 16.sp,
            color = Colors.DarkOrange.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun AuthButtonsSection(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sign In Button
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.DarkOrange
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Text(
                text = "Sign In",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Divider with "or" text
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Colors.DarkOrange.copy(alpha = 0.3f)
            )

            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "or",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = Colors.DarkOrange.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Colors.DarkOrange.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Up Button
        OutlinedButton(
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Colors.DarkOrange,
                containerColor = Color.Transparent
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Colors.Orange, Colors.DarkOrange)
                )
            )
        ) {
            Text(
                text = "Create Account",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Google Sign In Button (commented out as in original)
        /*
        OutlinedButton(
            onClick = onGoogleSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Colors.DarkOrange
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Colors.Orange.copy(alpha = 0.5f), Colors.DarkOrange.copy(alpha = 0.5f))
                )
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Google Icon placeholder
                Card(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Colors.Orange.copy(alpha = 0.1f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "G",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Colors.DarkOrange
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Continue with Google",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.DarkOrange
                )
            }
        }
        */

        // Additional spacing for better visual balance
        Spacer(modifier = Modifier.height(32.dp))

        // Terms and privacy text
        Text(
            text = "By continuing, you agree to our Terms of Service\nand Privacy Policy",
            fontSize = 12.sp,
            color = Colors.DarkOrange.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
    }
}
