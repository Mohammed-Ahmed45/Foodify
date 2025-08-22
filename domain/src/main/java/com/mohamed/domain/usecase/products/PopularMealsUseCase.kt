package com.mohamed.domain.usecase.products

import com.mohamed.domain.model.products.Products
import com.mohamed.domain.repositories.product.PopularMealsRepo
import javax.inject.Inject

class PopularMealsUseCase @Inject constructor(
    private val popularMealsRepo: PopularMealsRepo,
) {
    suspend fun invoke(): List<Products> {
        return popularMealsRepo.popularMeals()
    }
}