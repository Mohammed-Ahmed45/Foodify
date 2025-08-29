package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun OrderCalculationsSection(calculations: OrderCalculations) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CalculationRow("Subtotal", calculations.subTotal.toFloat())
        CalculationRow("Discount (15%)", -calculations.discount, isDiscount = true)
        CalculationRow("Delivery Fee", calculations.deliveryFee)
        CalculationRow("Tax (14%)", calculations.tax)

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Colors.DividerColor
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Colors.Primary.copy(alpha = 0.05f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            CalculationRow(
                "Final Total",
                calculations.finalTotal,
                isTotal = true,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}