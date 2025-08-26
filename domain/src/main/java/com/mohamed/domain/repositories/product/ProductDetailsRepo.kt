package com.mohamed.domain.repositories.product

import com.mohamed.domain.model.products.Products

interface ProductDetailsRepo {
    suspend fun productDetails(productId: String): Products?
}