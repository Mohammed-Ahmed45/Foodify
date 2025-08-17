package com.mohamed.data.model

import com.mohamed.domain.model.category.Category

data class CategoryDto(
    val id: String ="",
    val name: String ="",
    val imageUrl: String =""
){
    fun toCategory():Category{
       return Category(
            id = id,
            name = name,
            imageUrl = imageUrl
        )
    }
}