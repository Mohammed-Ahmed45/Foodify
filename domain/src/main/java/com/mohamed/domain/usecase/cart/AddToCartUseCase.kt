package com.mohamed.domain.usecase.cart

import com.mohamed.domain.repositories.cart.CartRepo
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepo: CartRepo
){
    suspend fun invoke(userId: String, productId: String) {
        return cartRepo.addToCart(userId,productId)
    }
}