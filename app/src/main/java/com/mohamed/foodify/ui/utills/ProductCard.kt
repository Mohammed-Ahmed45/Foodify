package com.mohamed.foodify.ui.utills

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.mohamed.domain.model.products.Products
import com.mohamed.foodify.R
import com.mohamed.foodify.ui.navigation.Route
import com.mohamed.foodify.ui.theme.Colors
import com.mohamed.foodify.ui.viewmodel.CartViewModel


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    products: Products,
    cartViewModel: CartViewModel = hiltViewModel(),
    elevation: Dp = 6.dp,
) {
    var isPressed by remember { mutableStateOf(false) }


    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.7f),
        label = "press_scale"
    )



    Card(
        modifier = modifier
            .padding(8.dp)
            .height(300.dp)
            .width(200.dp)
            .scale(pressScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    radius = 100.dp,
                    color = Colors.Red.copy(alpha = 0.1f)
                )
            ) {
                navController.navigate("${Route.PRODUCT_DETAILS}/${products.id}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = products.imagesUrl.firstOrNull(),
                    contentDescription = products.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.beef),
                    placeholder = painterResource(R.drawable.beef)
                )
                Log.d("TEST_IMAGES", products.imagesUrl.toString())
            }

            // Product Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp)
            ) {
                Text(
                    text = products.title.takeIf { it.isNotBlank() } ?: "Product Name",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Colors.DarkOrange,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = products.description.takeIf { it.isNotBlank() } ?: "Product Description",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${products.price.takeIf { it.isNotBlank() } ?: "0"} EGP",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.DarkOrange
                    )

                    Box(
                        modifier = Modifier.size(50.dp),
                        contentAlignment = Alignment.Center
                    ) {


                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Colors.DarkOrange,
                                    shape = CircleShape
                                )


                                .clickable {
                                    cartViewModel.addToCart(products.id)
                                    cartViewModel.showAnimation = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add to Cart",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }


                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ProductCardPreview() {
    val navController = rememberNavController()
    val product = Products(
        id = "1",
        title = "Sample Product",
        description = "This is a sample product description.",
        price = "19.99",
        category = "Electronics",
        imagesUrl = listOf("https://via.placeholder.com/150")
    )
    ProductCard(
        navController = navController,
        products = product,
    )
}
