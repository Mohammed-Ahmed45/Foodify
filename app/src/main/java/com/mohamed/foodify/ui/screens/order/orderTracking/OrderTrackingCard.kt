package com.mohamed.foodify.ui.screens.order.orderTracking


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.domain.model.orders.OrdersEntity
import com.mohamed.domain.model.products.Products
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun OrderTrackingCard(
    order: OrdersEntity,
    products: List<Products>,
) {


    val statusColor = when (order.status) {
        "ORDERED" -> Colors.LightOrange
        "IN_PROGRESS" -> Colors.DarkBlue
        "DELIVERED" -> Colors.LightGreen
        "CANCELLED" -> Colors.DarkRed
        else -> Colors.DarkGray
    }

    val statusText = when (order.status) {
        "ORDERED" -> "Ordered"
        "IN_PROGRESS" -> "In Progress"
        "DELIVERED" -> "Delivered"
        "CANCELLED" -> "Cancelled"
        else -> order.status ?: "Unknown"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Order Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status:",
                    fontSize = 14.sp,
                    color = Colors.DarkGray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = statusText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor,
                    modifier = Modifier
                        .background(
                            statusColor.copy(alpha = 0.15f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(
                    text = "Address: ",
                    fontSize = 14.sp,
                    color = Colors.DarkGray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (order.address.isNullOrBlank()) "Not specified" else order.address,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "Items:",
                fontSize = 14.sp,
                color = Colors.DarkGray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Colors.LightGray.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    order.cartItems?.forEach { (productId, quantity) ->
                        val product = products.find { it.id == productId }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = product?.title ?: "Unknown Item",
                                fontSize = 13.sp,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Qty: $quantity",
                                fontSize = 13.sp,
                                color = Colors.DarkOrange,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

        }
    }
}

