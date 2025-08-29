package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun OrderItemRow(
    productName: String,
    quantity: Int,
    unitPrice: Float,
    totalPrice: Float,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = productName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Colors.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$quantity × ${String.format("%.2f", unitPrice)} EGP",
                style = MaterialTheme.typography.bodySmall,
                color = Colors.TextSecondary
            )
        }

        Text(
            text = "${String.format("%.2f", totalPrice)} EGP",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Colors.Primary
        )
    }
}