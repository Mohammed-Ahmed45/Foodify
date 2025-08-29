package com.mohamed.foodify.ui.utills

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mohamed.foodify.R
import com.mohamed.foodify.ui.viewmodel.OrdersViewModel
import kotlinx.coroutines.delay

@Composable
fun OrderAnimation(
    modifier: Modifier = Modifier,
    ordersViewModel: OrdersViewModel = hiltViewModel(),
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.order_delivery)
    )
    LaunchedEffect(ordersViewModel.showAnimation) {
        if (ordersViewModel.showAnimation) {
            delay(4000)
            ordersViewModel.showAnimation = false
        }
    }


    LottieAnimation(
        composition = lottieComposition,
        iterations = 1,
        modifier = Modifier
            .size(300.dp)

    )

}