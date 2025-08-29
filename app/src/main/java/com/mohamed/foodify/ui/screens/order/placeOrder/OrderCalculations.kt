package com.mohamed.foodify.ui.screens.order.placeOrder

data class OrderCalculations(
    val subTotal: Long,
    val discount: Float,
    val deliveryFee: Float,
    val tax: Float,
    val finalTotal: Float,
)