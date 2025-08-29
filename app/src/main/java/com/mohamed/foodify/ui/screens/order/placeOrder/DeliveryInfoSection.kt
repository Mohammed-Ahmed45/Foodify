package com.mohamed.foodify.ui.screens.order.placeOrder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.viewmodel.AuthViewModel

@Composable
fun DeliveryInfoSection(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        authViewModel.loadCurrentUser()
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Address
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = Colors.Success,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = "Delivery Address",
                    style = MaterialTheme.typography.bodySmall,
                    color = Colors.TextSecondary
                )
                Text(
                    text = authViewModel.currentUser?.address ?: "Address not specified",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Delivery Time
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = null,
                tint = Colors.Accent,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = "Estimated Delivery Time",
                    style = MaterialTheme.typography.bodySmall,
                    color = Colors.TextSecondary
                )
                Text(
                    text = "30-45 minutes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.Accent,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Phone Number
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.Phone,
                contentDescription = null,
                tint = Colors.Primary,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = "Phone Number",
                    style = MaterialTheme.typography.bodySmall,
                    color = Colors.TextSecondary
                )
                Text(
                    text = authViewModel.currentUser?.phoneNumber ?: "Phone not specified",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
