package com.mohamed.domain.usecase.products

import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.ProductsRepo
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val productsRepo: ProductsRepo,
) {
    suspend fun invoke(productId: String): List<Products> {
        return productsRepo.products(productId)
    }
}