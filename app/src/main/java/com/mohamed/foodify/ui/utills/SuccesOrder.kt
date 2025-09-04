package com.mohamed.foodify.ui.utills

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohamed.foodify.R

@Composable
fun SuccessOrder(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_success_order),
            contentDescription = "",
            modifier = modifier.size(260.dp)
        )
        Text(
            text = "Thank You!",
            style = typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center

        )
        Text(
            text = "Your Order has been received",
            style = typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold

        )
    }


}

@Preview(showSystemUi = true)
@Composable
fun SuccessOrderPreview() {
    val navController = rememberNavController()
    SuccessOrder(navController = navController)
}
