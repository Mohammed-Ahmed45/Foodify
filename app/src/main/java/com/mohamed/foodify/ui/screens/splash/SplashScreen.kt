package com.mohamed.foodify.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mohamed.foodify.R
import com.mohamed.foodify.ui.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {
    LaunchedEffect(Unit){
        delay(1000)
        navController.navigate(Route.AUTH_SCREEN){
            popUpTo(Route.SPLASH){
                inclusive=true
            }
        }

    }

    Image(
        painter = painterResource(R.drawable.img_splash),
        contentDescription = ""
    )
}