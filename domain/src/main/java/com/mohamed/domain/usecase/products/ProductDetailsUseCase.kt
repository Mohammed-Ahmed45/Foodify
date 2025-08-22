package com.mohamed.domain.usecase.products

import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.ProductDetailsRepo
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val productDetailsRepo: ProductDetailsRepo,
) {
    suspend fun invoke(productId: String): Products? {
        return productDetailsRepo.productDetails(productId)
    }
}