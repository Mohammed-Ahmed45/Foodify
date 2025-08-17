package com.mohamed.foodify.ui.utills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mohamed.foodify.R
import com.mohamed.data.model.CategoryDto
import com.mohamed.domain.model.category.Category
import com.mohamed.foodify.ui.navigation.Route

@Composable
fun CategoriesCard(
    modifier: Modifier = Modifier,
    navController: NavController?=null,
    category: Category
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = modifier.padding(10.dp)
                .size(62.dp)
                .clickable{
                    navController?.navigate("${Route.PRODUCTS_SCREEN}/${category.id}")
                }
        ) {
                AsyncImage(
                    model = category.imageUrl,
                    contentDescription = category.name,
                    placeholder = painterResource(id = R.drawable.beef),
                    error = painterResource(id = R.drawable.beef),
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
        }
        Text(category.name)

    }

}

@Preview(showSystemUi = true)
@Composable
private fun CategoriesCardPreview() {
    val category = Category(
        id = "1",
        name = "Beef",
        imageUrl = "https://www.themealdb.com/images/category/beef.png"
    )
    CategoriesCard(category = category)

}