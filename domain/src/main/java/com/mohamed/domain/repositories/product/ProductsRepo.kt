package com.mohamed.domain.repositories.product

import com.mohamed.domain.model.products.Products

interface ProductsRepo {
    suspend fun products(productId: String): List<Products>
}