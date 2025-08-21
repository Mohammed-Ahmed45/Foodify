package com.mohamed.domain.usecase.cart

import com.mohamed.domain.repositories.cart.CartRepo
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepo: CartRepo
) {
    suspend fun removeFromCart(userId: String, productId: String) {
        return cartRepo.removeFromCart(userId,productId)
    }
}