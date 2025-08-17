package com.mohamed.foodify.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohamed.foodify.ui.model.NavItemList
import com.mohamed.foodify.ui.screens.cart.CartScreen
import com.mohamed.foodify.ui.screens.home.HomeScreen
import com.mohamed.foodify.ui.screens.profile.ProfileScreen
import com.mohamed.foodify.ui.theme.Colors

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val navItemList = listOf(
        NavItemList("Home", Icons.Default.Home),
        NavItemList("Cart", Icons.Default.ShoppingCart),
        NavItemList("Profile", Icons.Default.Person)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = modifier.clip(shape= RoundedCornerShape(30.dp)),
        bottomBar = {
            NavigationBar {

                navItemList.forEachIndexed { index, navItem ->
                    val isSelected = selectedIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedIndex = index },
                        icon = { Icon(navItem.icon, contentDescription = navItem.title) },
                        label = {
                            AnimatedVisibility(
                                visible = isSelected,
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut()
                            ) {
                                Text(
                                    text = navItem.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Colors.Red
                                )
                            }
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Colors.Red,
                            selectedTextColor = Colors.Red,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black,
                            disabledTextColor = Color.Black,
                            selectedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            selectedIndex = selectedIndex,
            navController = navController,
            modifier=modifier.padding(innerPadding)
        )
    }
}
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        when(selectedIndex){
            0->HomeScreen(navController =navController)
            1->CartScreen(navController =navController)
            2->ProfileScreen(navController =navController)
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    val navController= rememberNavController()
    MainScreen(navController=navController)
}

