package com.mohamed.data.repo.cart

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamed.data.model.ProductDto
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.cart.CartRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepoImp @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): CartRepo {
    override suspend fun addToCart(userId: String, productId: String) {
        val userDoc = firebaseFirestore.collection("users").document(userId)

        firebaseFirestore.runTransaction { transaction ->
            val snapshot = transaction.get(userDoc)
            val currentCart = snapshot.get("cartItems") as? Map<String, Long> ?: emptyMap()
            val currentQuantity = currentCart[productId] ?: 0
            val updatedQuantity = currentQuantity + 1

            val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)
            transaction.update(userDoc, updatedCart)
        }.await()
    }
    override suspend fun getCartList(productIds: String):  List<Products> {
        val products = mutableListOf<Products>()

        val snapshot = firebaseFirestore.collection("data")
                .document("stock")
                .collection("products")
                .document(productIds)
                .get()
                .await()

           snapshot.toObject(ProductDto::class.java)?.let{ dto ->
            products.add(dto.toProduct())
        }
        return products

    }

    override suspend fun getAllCartList(productIds: List<String>): List<Products> {
        val products = mutableListOf<Products>()

        for (id in productIds) {
            val snapshot = firebaseFirestore.collection("data")
                .document("stock")
                .collection("products")
                .document(id)
                .get()
                .await()

            snapshot.toObject(ProductDto::class.java)?.let { dto ->
                products.add(dto.toProduct())
            }
        }

        return products
    }

    override suspend fun removeFromCart(userId: String, productId: String) {
        val userDoc = firebaseFirestore.collection("users").document(userId)

        firebaseFirestore.runTransaction { transaction ->
            val snapshot = transaction.get(userDoc)
            val currentCart = snapshot.get("cartItems") as? Map<String, Long> ?: emptyMap()
            val currentQuantity = currentCart[productId] ?: 0
            val updatedQuantity = currentQuantity -1

            val updatedCart =
                if (updatedQuantity <=0) {
                    mapOf("cartItems.$productId" to FieldValue.delete())
                } else
                mapOf("cartItems.$productId" to updatedQuantity)
            transaction.update(userDoc, updatedCart)
        }.await()
    }


}