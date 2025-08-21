package com.mohamed.domain.usecase.cart

import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.cart.CartRepo
import javax.inject.Inject

class GetCartListUseCase@Inject constructor(
    private val cartRepo: CartRepo
){
    suspend fun invoke(productId: String): List<Products> {
        return cartRepo.getCartList(productId)
    }

}
