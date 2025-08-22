package com.mohamed.foodify.ui.utills

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mohamed.foodify.ui.viewmodel.ProductsViewModel
import kotlin.math.abs

@Composable
fun PopularMealsSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    productsViewModel: ProductsViewModel,
) {
    val lazyGridState = rememberLazyGridState()

    Text(
        "Popular Meals",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(12.dp))

    LazyHorizontalGrid(
        state = lazyGridState,
        rows = GridCells.Fixed(1),
        modifier = modifier
            .height(340.dp)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true,
    ) {
        itemsIndexed(productsViewModel.popularMealsList) { index, popularMealsItems ->


            val layoutInfo = lazyGridState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2

            val itemInfo = visibleItemsInfo.find { it.index == index }

            var isCenterItem = false
            var distanceFromCenter = 1000f

            if (itemInfo != null) {
                val itemCenter = itemInfo.offset.x + itemInfo.size.width / 2
                distanceFromCenter = abs(itemCenter - viewportCenter).toFloat()

                // البحث عن أقرب عنصر للمركز
                var minDistance = distanceFromCenter
                visibleItemsInfo.forEach { info ->
                    val otherItemCenter = info.offset.x + info.size.width / 2
                    val otherDistance = abs(otherItemCenter - viewportCenter).toFloat()
                    if (otherDistance < minDistance) {
                        minDistance = otherDistance
                    }
                }
                isCenterItem = distanceFromCenter == minDistance
            }

            val targetScale = if (isCenterItem) 1.15f else 1f
            val targetAlpha = if (isCenterItem) 1f else 0.5f
            val targetElevation = if (isCenterItem) 12.dp else 4.dp

            val animatedScale by animateFloatAsState(
                targetValue = targetScale,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "scale_animation"
            )

            val animatedAlpha by animateFloatAsState(
                targetValue = targetAlpha,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "alpha_animation"
            )

            val animatedElevation by animateDpAsState(
                targetValue = targetElevation,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "elevation_animation"
            )

            ProductCard(
                navController = navController,
                products = popularMealsItems,
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = animatedScale,
                        scaleY = animatedScale,
                        alpha = animatedAlpha
                    ),
                elevation = animatedElevation
            )
        }
    }
}

