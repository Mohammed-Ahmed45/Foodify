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
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import kotlinx.coroutines.delay


@Composable
fun AddToCartLottieAnimation(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.add_to_cart_success)
    )
    LaunchedEffect(cartViewModel.showAnimation) {
        if (cartViewModel.showAnimation) {
            delay(2000)
            cartViewModel.showAnimation = false

        }
    }


    LottieAnimation(
        composition = lottieComposition,
        iterations = 1,
        modifier = Modifier
            .size(180.dp)

    )

}
