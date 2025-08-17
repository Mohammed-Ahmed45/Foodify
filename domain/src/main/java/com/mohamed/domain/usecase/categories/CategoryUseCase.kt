package com.mohamed.domain.usecase.categories

import com.mohamed.domain.model.category.Category
import com.mohamed.domain.repositories.category.CategoriesRepo
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) {
    suspend fun invoke(): List<Category>{
        return categoriesRepo.getCategories()
    }
}