package com.mohamed.foodify.ui.screens.order.orderTracking


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun StatusChip(
    label: String,
    count: Int,
    status: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (isSelected) color.copy(alpha = 0.15f) else Colors.LightGray.copy(alpha = 0.3f),
                RoundedCornerShape(20.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) color else Colors.DarkGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) color else Colors.DarkGray
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (isSelected) color else Colors.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}
