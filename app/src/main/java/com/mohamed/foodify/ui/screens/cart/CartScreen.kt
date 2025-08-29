package com.mohamed.foodify.ui.screens.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.utills.LoadingAnimation
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import com.mohamed.foodify.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.delay

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    cartId: String = ""
) {

    LaunchedEffect (Unit) {
        authViewModel.loadCurrentUser()
    }

    val subTotal by remember {
        derivedStateOf {
            var total = 0f
            cartViewModel.cartProducts.forEach { product ->
                val quantity = authViewModel.currentUser?.cartItems?.get(product.id) ?: 0L
                total += product.price.toFloat() * quantity
            }
            total
        }
    }

    LaunchedEffect(authViewModel.currentUser) {
        val productIds = authViewModel.currentUser?.cartItems?.keys?.toList() ?: emptyList()
        if (productIds.isNotEmpty()) {
            delay(1000)
            cartViewModel.getAllCartList(productIds)
        }
        if (cartViewModel.cartProducts.isEmpty()) {
            cartViewModel.getAllCartList(productIds)
        }
    }

    if (cartViewModel.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation()
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {

            items(cartViewModel.cartProducts) { product ->
                val quantity = authViewModel.currentUser?.cartItems?.get(product.id) ?: 0L
                CartItemsView(
                    product = product,
                    quantity = quantity,
                    navController = navController
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Route.ORDER_SCREEN) {
                    popUpTo(Route.CART_SCREEN) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Colors.DarkOrange)
        ) {
            Text(
                "Checkout - ${String.format("%.2f", subTotal)} EGP",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun CartScreenPreview() {
    val navController = rememberNavController()
    CartScreen(navController = navController)
}