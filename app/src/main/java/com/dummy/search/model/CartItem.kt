package com.dummy.search.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String
)
