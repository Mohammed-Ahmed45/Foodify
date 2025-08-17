package com.mohamed.foodify.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.domain.model.category.Category
import com.mohamed.foodify.ui.utills.CategoriesCard
import com.mohamed.foodify.ui.viewmodel.CategoriesViewModel

@Composable
fun CategoriesContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoriesViewModel: CategoriesViewModel= hiltViewModel()
) {
    LaunchedEffect(Unit) {
        categoriesViewModel.getCategories()
    }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(categoriesViewModel.categoriesList){category->
            CategoryItem(
                navController=navController,
              categoryList =category
            )

        }
    }

}

@Composable
fun CategoryItem(categoryList: Category, navController: NavController) {
    CategoriesCard(
        category = categoryList,
        navController = navController
    )
}





