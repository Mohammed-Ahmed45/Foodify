package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun OrderBottomBar(
    finalTotal: Float,
    onConfirmOrder: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 16.dp
    ) {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = Colors.DividerColor
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Final Total",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.TextSecondary
                    )
                    Text(
                        text = "${String.format("%.2f", finalTotal)} EGP",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Colors.Primary
                    )
                }

                Button(
                    onClick = onConfirmOrder,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colors.Primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "Confirm Order",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}