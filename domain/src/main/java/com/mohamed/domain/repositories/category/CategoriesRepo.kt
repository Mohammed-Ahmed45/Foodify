package com.mohamed.domain.repositories.category

import com.mohamed.domain.model.category.Category

interface CategoriesRepo {
    suspend fun getCategories(): List<Category>
}