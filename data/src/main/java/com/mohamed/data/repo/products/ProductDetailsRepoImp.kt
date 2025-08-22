package com.mohamed.data.repo.products

import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.ProductDto
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.ProductDetailsRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductDetailsRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : ProductDetailsRepo {
    override suspend fun productDetails(productId: String): Products? {
        val response = firebaseFirestore.collection("data")
            .document("stock")
            .collection("products")
            .document(productId)
            .get()
            .await()
        return response.toObject(ProductDto::class.java)?.toProduct()

    }
}