package com.mohamed.foodify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohamed.foodify.ui.screens.auth.AuthScreen
import com.mohamed.foodify.ui.screens.auth.signin.SignInScreen
import com.mohamed.foodify.ui.screens.auth.signup.SignUpScreen
import com.mohamed.foodify.ui.screens.home.HomeScreen
import com.mohamed.foodify.ui.screens.main.MainScreen
import com.mohamed.foodify.ui.screens.products.ProductsScreen
import com.mohamed.foodify.ui.screens.splash.SplashScreen


object Route{
    const val SPLASH="splash"
    const val AUTH_SCREEN="auth"
    const val SIGNUP="signup"
    const val SIGN_IN="signin"
    const val MAIN_SCREEN="main_screen"
    const val HOME_SCREEN="home_screen"
    const val PRODUCTS_SCREEN="products"
    const val CATEGORY_ID="categoryId"
}
@Composable
fun AppNavigation(modifier: Modifier) {
    val navController= rememberNavController()

    NavHost(navController=navController, startDestination = Route.SPLASH){
        composable(route = Route.SPLASH){ SplashScreen(navController=navController) }
        composable(Route.SIGNUP) { SignUpScreen(navController = navController) }
        composable(Route.HOME_SCREEN) { HomeScreen(navController=navController) }
        composable(Route.SIGN_IN) { SignInScreen(navController=navController) }
        composable(Route.AUTH_SCREEN) { AuthScreen(navController=navController) }
        composable(Route.MAIN_SCREEN) { MainScreen(navController=navController) }
        composable(
            route ="${Route.PRODUCTS_SCREEN}/{${Route.CATEGORY_ID}}",
            arguments =listOf(navArgument(name = Route.CATEGORY_ID){ type= NavType.StringType })
            ){backStack->
            val productId=backStack.arguments?.getString(Route.CATEGORY_ID)
            ProductsScreen(
                navController=navController,
                productId = productId
            )
            }


    }
}