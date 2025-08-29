package com.mohamed.foodify.ui.screens.order.orderTracking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun OrderTrackingStatusCard(
    orders: List<OrdersEntity>,
    selectedStatus: String?,
    onStatusSelected: (String?) -> Unit,
) {
    val totalOrders = orders.size
    val orderedCount = orders.count { it.status == "ORDERED" }
    val deliveredCount = orders.count { it.status == "DELIVERED" }
    val inProgressCount = orders.count { it.status == "IN_PROGRESS" }
    val cancelledCount = orders.count { it.status == "CANCELLED" }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order Statistics",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Colors.DarkOrange,
                modifier = Modifier.padding(bottom = 12.dp)
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStatusSelected(null) }
                    .background(
                        if (selectedStatus == null) Colors.DarkOrange.copy(alpha = 0.1f) else Color.Transparent,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Orders",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = totalOrders.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.DarkOrange
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusChip(
                    label = "Ordered",
                    count = orderedCount,
                    status = "ORDERED",
                    isSelected = selectedStatus == "ORDERED",
                    color = Colors.LightOrange,
                    onClick = { onStatusSelected(if (selectedStatus == "ORDERED") null else "ORDERED") }
                )

                StatusChip(
                    label = "In Progress",
                    count = inProgressCount,
                    status = "IN_PROGRESS",
                    isSelected = selectedStatus == "IN_PROGRESS",
                    color = Colors.DarkBlue,
                    onClick = { onStatusSelected(if (selectedStatus == "IN_PROGRESS") null else "IN_PROGRESS") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusChip(
                    label = "Delivered",
                    count = deliveredCount,
                    status = "DELIVERED",
                    isSelected = selectedStatus == "DELIVERED",
                    color = Colors.LightGreen,
                    onClick = { onStatusSelected(if (selectedStatus == "DELIVERED") null else "DELIVERED") }
                )

                StatusChip(
                    label = "Cancelled",
                    count = cancelledCount,
                    status = "CANCELLED",
                    isSelected = selectedStatus == "CANCELLED",
                    color = Colors.DarkRed,
                    onClick = { onStatusSelected(if (selectedStatus == "CANCELLED") null else "CANCELLED") }
                )
            }
        }
    }
}
