package com.mohamed.foodify.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohamed.foodify.ui.screens.categories.CategoriesContent
import com.mohamed.foodify.ui.utills.LottieAnimationState
import com.mohamed.foodify.ui.utills.PopularMealsSection
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import com.mohamed.foodify.ui.viewmodel.CartViewModel
import com.mohamed.foodify.ui.viewmodel.ProductsViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
) {


    LaunchedEffect(Unit) {
        authViewModel.loadCurrentUser()

    }

    LaunchedEffect(Unit) {
        productsViewModel.getPopularMeals()
    }
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        Text(
            "Hello,",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(authViewModel.currentUser?.name?:"",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        Text(
            "What do you want to eat?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Light
        )

        CategoriesContent(
            navController=navController
        )

        Spacer(modifier = Modifier.height(12.dp))
        PopularMealsSection(
            navController = navController,
            productsViewModel = productsViewModel
        )
    }
    if (cartViewModel.showAnimation) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        ) {

            LottieAnimationState()

        }
    }



}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

