package com.dummy.search.repository

import com.dummy.search.database.CartDao
import com.dummy.search.model.CartItem

class CartRepository(private val cartDao: CartDao) {

    suspend fun addToCart(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }
}
