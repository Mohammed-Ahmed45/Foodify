package com.mohamed.foodify.ui.screens.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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


    LaunchedEffect(authViewModel.currentUser) {
        val productIds = authViewModel.currentUser?.cartItems?.keys?.toList() ?: emptyList()
        if (productIds.isNotEmpty()) {
            delay(1000)
            cartViewModel.getAllCartList(productIds)

        }
    }


    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(cartViewModel.cartProducts) { product->
            var quantity = authViewModel.currentUser?.cartItems?.get(product.id) ?: 0
            CartItemsView(
                product = product,
                quantity = quantity

            )
        }
    }
}
