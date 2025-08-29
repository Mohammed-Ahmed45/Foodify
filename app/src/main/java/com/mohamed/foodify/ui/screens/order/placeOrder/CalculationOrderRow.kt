package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.mohamed.foodify.ui.theme.Colors
import kotlin.math.abs


@Composable
fun CalculationRow(
    label: String,
    amount: Float,
    modifier: Modifier = Modifier,
    isDiscount: Boolean = false,
    isTotal: Boolean = false,

    ) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = if (isTotal) Colors.Primary else Colors.TextSecondary
        )

        Text(
            text = "${if (isDiscount) "-" else ""}${String.format("%.2f", abs(amount))} EGP",
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
            color = when {
                isTotal -> Colors.Primary
                isDiscount -> Colors.Discount
                else -> Colors.TextPrimary
            }
        )
    }
}

