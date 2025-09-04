package com.mohamed.foodify.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohamed.adminFoodify.screen.main.AdminMainScreen
import com.mohamed.foodify.ui.screens.auth.AuthScreen
import com.mohamed.foodify.ui.screens.auth.signin.SignInScreen
import com.mohamed.foodify.ui.screens.auth.signup.SignUpScreen
import com.mohamed.foodify.ui.screens.cart.CartScreen
import com.mohamed.foodify.ui.screens.home.HomeScreen
import com.mohamed.foodify.ui.screens.main.MainScreen
import com.mohamed.foodify.ui.screens.order.orderTracking.OrderTrackingScreen
import com.mohamed.foodify.ui.screens.order.placeOrder.OrderScreen
import com.mohamed.foodify.ui.screens.products.ProductDetails
import com.mohamed.foodify.ui.screens.products.ProductsScreen
import com.mohamed.foodify.ui.screens.splash.SplashScreen
import com.mohamed.foodify.ui.utills.SuccessOrder


object Route{
    const val SPLASH="splash"
    const val AUTH_SCREEN="auth"
    const val SIGNUP="signup"
    const val SIGN_IN="signin"
    const val MAIN_SCREEN="main_screen"
    const val HOME_SCREEN="home_screen"
    const val PRODUCTS_SCREEN="products"
    const val CATEGORY_ID="categoryId"
    const val PRODUCT_DETAILS="Product_Details"
    const val PRODUCT_ID="productId"
    const val CART_SCREEN="cart_screen"
    const val ADMIN_SCREEN="admin_screen"
    const val ORDER_SCREEN = "order_screen"
    const val ORDER_TRACKING_SCREEN = "order_tracking_screen"
    const val SUCCESS_ORDER = "Success_Order"
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
        composable(Route.CART_SCREEN) { CartScreen(navController=navController) }
        composable(Route.ADMIN_SCREEN) { AdminMainScreen(navController=navController) }
        composable(Route.ORDER_SCREEN) { OrderScreen(navController = navController) }
        composable(Route.ORDER_TRACKING_SCREEN) { OrderTrackingScreen(navController = navController) }
        composable(Route.SUCCESS_ORDER) { SuccessOrder(navController = navController) }
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
        composable(
            route = "${Route.PRODUCT_DETAILS}/{${Route.PRODUCT_ID}}"
            ,
            arguments = listOf(
                navArgument(name =Route.PRODUCT_ID){
                    NavType.StringType
                }
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        700, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(700, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        700, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(700, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {backStack->
            val productId=backStack.arguments?.getString(Route.PRODUCT_ID)
            ProductDetails(
                productId = productId!!,
                navController = navController
            )
        }


    }
}