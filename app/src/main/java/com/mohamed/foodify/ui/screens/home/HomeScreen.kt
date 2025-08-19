package com.mohamed.foodify.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.mohamed.foodify.ui.screens.categories.CategoriesContent
import com.mohamed.foodify.ui.utills.ProductCard
import com.mohamed.foodify.ui.viewmodel.AuthViewModel
import com.mohamed.foodify.ui.viewmodel.ProductsViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
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
        Text(
            "Popular Meals",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = modifier.height(340.dp)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true,
        ) {
            items(productsViewModel.popularMealsList){popularMealsItems->
                ProductCard(
                    navController = navController,
                    products = popularMealsItems
                )

            }
        }
    }



}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

