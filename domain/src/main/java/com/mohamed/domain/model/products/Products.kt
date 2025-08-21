package com.mohamed.domain.model.products


data class Products(
    val id: String="",
    val title: String="",
    val description: String="",
    val price: String="",
    val category: String="",
    val imagesUrl: List<String> = emptyList()

)
