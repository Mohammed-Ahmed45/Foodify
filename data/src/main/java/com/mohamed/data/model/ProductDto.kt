package com.mohamed.data.model

import com.mohamed.domain.model.products.Products

data class ProductDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imagesUrl: List<String> = emptyList(),
) {
    fun toProduct(): Products {
        return Products(
            id = id,
            title = title,
            description = description,
            price = price,
            category = category,
            imagesUrl = imagesUrl
        )
    }
}
