package com.dummy.search.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val rating: Double,
    val discountPercentage: Double,
    val thumbnail: String
)
