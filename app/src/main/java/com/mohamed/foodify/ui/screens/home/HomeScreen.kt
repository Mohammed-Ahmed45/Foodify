package com.mohamed.foodify.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.mohamed.foodify.ui.screens.categories.CategoriesContent
import com.mohamed.foodify.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
//    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember{mutableStateOf("")}
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnSuccessListener {task->
                name=task.get("name").toString()
            }
            .addOnFailureListener {
                name=""
            }

    }
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        Text(
            "Hello,",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        Text(
            "What do you want to eat?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Light
        )

        CategoriesContent()
        Text(
            "Popular Meals",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }



}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

