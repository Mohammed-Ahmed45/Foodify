package com.mohamed.data.repo.products

import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.ProductDto
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.PopularMealsRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PopularMealsRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : PopularMealsRepo {
    override suspend fun popularMeals(): List<Products> {
        val response = firebaseFirestore.collection("data")
            .document("stock")
            .collection("products")
            .whereEqualTo("isPopular", true)
            .get()
            .await()
        return response.documents.mapNotNull { docMeals ->
            docMeals.toObject(ProductDto::class.java)?.toProduct()
        }
    }
}