package com.mohamed.foodify.ui.screens.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.utills.LoadingAnimation
import com.mohamed.foodify.ui.utills.LottieAnimationState
import com.mohamed.foodify.ui.utills.ProductCard
import com.mohamed.foodify.ui.utills.SearchBar
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import com.mohamed.foodify.ui.viewmodel.ProductsViewModel

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    productId: String? = "",
    navController: NavController,
) {
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        productsViewModel.getProduct(productId!!)
    }

    if (productsViewModel.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        ) {
            LoadingAnimation()
        }
    }

    val filterProducts = if (searchQuery.isEmpty()) {
        productsViewModel.productList
    } else {
        productsViewModel.productList.filter { product ->
            product.title.contains(searchQuery, ignoreCase = true)
                    && product.description.contains(searchQuery, ignoreCase = true)
        }
    }
    Column {
        Row(modifier = modifier.padding(top = 16.dp)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                }
            )

            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = Colors.DarkOrange,
                modifier = modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate(Route.CART_SCREEN) {
                            popUpTo(Route.PRODUCTS_SCREEN) {
                                inclusive = true
                            }
                        }
                    }
                    .clickable {
                        navController.navigate(Route.CART_SCREEN) {
                            popUpTo(Route.HOME_SCREEN) {
                                inclusive = true
                            }
                        }
                    }
                    .size(28.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }

        Box(
            modifier = modifier.fillMaxSize()
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),


                ) {
                items(filterProducts) { product ->
                    ProductCard(
                        products = product,
                        navController = navController
                    )

                }

            }
        }


    }
    if (cartViewModel.showAnimation) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        ) {

            LottieAnimationState()

        }
    }

}

