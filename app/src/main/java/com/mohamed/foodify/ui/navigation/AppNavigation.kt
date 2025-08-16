package com.mohamed.foodify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohamed.foodify.ui.screens.auth.AuthScreen
import com.mohamed.foodify.ui.screens.auth.signin.SignInScreen
import com.mohamed.foodify.ui.screens.auth.signup.SignUpScreen
import com.mohamed.foodify.ui.screens.home.HomeScreen
import com.mohamed.foodify.ui.screens.main.MainScreen
import com.mohamed.foodify.ui.screens.splash.SplashScreen


object Route{
    const val SPLASH="splash"
    const val AUTH_SCREEN="auth"

}
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController= rememberNavController()

    NavHost(navController=navController, startDestination = Route.SPLASH){
        composable(route = Route.SPLASH){ SplashScreen(navController=navController) }
        composable(Route.AUTH_SCREEN) { AuthScreen(navController=navController) }
        composable(Route.SIGNUP) { SignUpScreen(navController = navController) }
        composable(Route.HOME_SCREEN) { HomeScreen(navController=navController) }
        composable(Route.SIGN_IN) { SignInScreen(navController=navController) }
        composable(Route.MAIN_SCREEN) { MainScreen(navController=navController) }


    }
}