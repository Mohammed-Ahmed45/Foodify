package com.mohamed.foodify.ui.utills

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mohamed.foodify.R

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )


    LottieAnimation(
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .size(180.dp)
    )

}
