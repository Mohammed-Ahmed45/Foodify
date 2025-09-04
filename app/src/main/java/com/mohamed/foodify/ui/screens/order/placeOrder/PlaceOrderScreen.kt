package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import calculateOrderTotals
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import com.mohamed.foodify.ui.viewmodel.OrdersViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    ordersViewModel: OrdersViewModel = hiltViewModel(),
    navController: NavController,
) {

    val orderCalculations by remember {
        derivedStateOf {
            calculateOrderTotals(
                cartViewModel.cartProducts,
                authViewModel.currentUser?.cartItems ?: emptyMap()
            )
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.loadCurrentUser()
    }

    LaunchedEffect(authViewModel.currentUser) {
        val productIds = authViewModel.currentUser?.cartItems?.keys?.toList() ?: emptyList()
        if (productIds.isNotEmpty()) {
            delay(1000)
            cartViewModel.getAllCartList(productIds)
        }
    }

    Scaffold(
        containerColor = Colors.Background,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .height(62.dp),
                title = {
                    Text(
                        "Confirm Order",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Colors.TextPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            if (!ordersViewModel.showAnimation) {
                OrderBottomBar(

                    finalTotal = orderCalculations.finalTotal,
                    onConfirmOrder = {
                        if (orderCalculations.subTotal >= 1) {
                            ordersViewModel.sendUserOrdes()
                            navController.navigate(Route.SUCCESS_ORDER)
                        } else {
                            ordersViewModel.showAnimation = false
                        }

                    }

                )
            }
        }
    ) { paddingValues ->

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Colors.Background),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Order Items Section
                item {
                    OrderSectionCard(
                        title = "Order Items",
                        icon = Icons.Outlined.ShoppingCart,
                        iconColor = Colors.Primary
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            cartViewModel.cartProducts.forEach { product ->
                                val quantity =
                                    authViewModel.currentUser?.cartItems?.get(product.id) ?: 0L
                                OrderItemRow(
                                    productName = product.title,
                                    quantity = quantity.toInt(),
                                    unitPrice = product.price.toFloat(),
                                    totalPrice = product.price.toFloat() * quantity
                                )
                            }
                        }
                    }
                }

                // Bill Details Section
                item {
                    OrderSectionCard(
                        title = "Bill Details",
                        icon = Icons.Default.Receipt,
                        iconColor = Colors.Accent
                    ) {
                        OrderCalculationsSection(orderCalculations)
                    }
                }

                // Delivery Information Section
                item {
                    OrderSectionCard(
                        title = "Delivery Information",
                        icon = Icons.Default.LocalShipping,
                        iconColor = Colors.Success
                    ) {
                        DeliveryInfoSection()
                    }
                }

                // Extra spacing for bottom bar
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }







