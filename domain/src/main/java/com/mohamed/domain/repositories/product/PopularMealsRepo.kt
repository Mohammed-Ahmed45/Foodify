package com.mohamed.domain.repositories.product

import com.mohamed.domain.model.products.Products

interface PopularMealsRepo {
    suspend fun popularMeals(): List<Products>
}