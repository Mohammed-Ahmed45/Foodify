package com.mohamed.foodify.ui.screens.order.orderTracking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.utills.LoadingAnimation
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import com.mohamed.foodify.ui.viewmodel.OrdersViewModel

@Composable
fun OrderTrackingScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    ordersViewModel: OrdersViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavController,
) {
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    val filteredOrders = remember(ordersViewModel.orderStatusList, selectedStatus) {
        if (selectedStatus == null) {
            ordersViewModel.orderStatusList.toList()
        } else {
            ordersViewModel.orderStatusList.filter { it.status == selectedStatus }
        }
    }
    val productIds = ordersViewModel.orderStatusList
        .flatMap { order -> order.cartItems?.keys ?: emptyList() }
        .distinct()

    LaunchedEffect(Unit) {
        authViewModel.loadCurrentUser()
        ordersViewModel.getUserOrder()

    }
    LaunchedEffect(productIds) {
        if (productIds.isNotEmpty()) {
            cartViewModel.getAllCartList(productIds)
        }
    }



    if (ordersViewModel.isLoading) {
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
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Order Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.DarkOrange,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        OrderTrackingStatusCard(
            orders = ordersViewModel.orderStatusList.toList(),
            selectedStatus = selectedStatus,
            onStatusSelected = { status -> selectedStatus = status }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Orders List
        if (ordersViewModel.orderStatusList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selectedStatus == null) "No orders found" else "No orders with status: $selectedStatus",
                    fontSize = 16.sp,
                    color = Colors.DarkGray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOrders) { order ->
                    OrderTrackingCard(
                        order = order,
                        products = cartViewModel.cartProducts
                    )
                }
            }
        }
    }
}



