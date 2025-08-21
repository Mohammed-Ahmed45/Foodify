package com.mohamed.domain.usecase.cart

import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.cart.CartRepo
import javax.inject.Inject

class GetAllCartListUseCase @Inject constructor(
    private val cartRepo: CartRepo
) {
    suspend operator fun invoke(productIds: List<String>): List<Products> {
        return cartRepo.getAllCartList(productIds)
    }
}
