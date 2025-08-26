package com.mohamed.data.repo.products

import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.ProductDto
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.ProductsRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : ProductsRepo {
    override suspend fun products(productId: String): List<Products> {
        val response = firebaseFirestore.collection("data").document("stock")
            .collection("products")
            .whereEqualTo("category", productId)
            .get()
            .await()
        return response.documents.mapNotNull { doc ->
            doc.toObject(ProductDto::class.java)?.toProduct()
        }
    }
}