package com.mohamed.domain.repositories.cart

import android.hardware.biometrics.BiometricManager
import com.mohamed.domain.model.products.Products

interface CartRepo {
    suspend fun addToCart(userId: String, productId: String)
    suspend fun getCartList(productIds: String):  List<Products>
    suspend fun getAllCartList(productIds: List<String>): List<Products>
    suspend fun removeFromCart(userId: String, productId: String)
}