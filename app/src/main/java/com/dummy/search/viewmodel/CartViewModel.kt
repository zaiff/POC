package com.dummy.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dummy.search.model.CartItem
import com.dummy.search.model.Product
import com.dummy.search.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getAllCartItems()
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val item = CartItem(
                id = product.id,
                title = product.title,
                price = product.price,
                thumbnail = product.thumbnail
            )
            repository.addToCart(item)
            loadCartItems()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem)
            loadCartItems()
        }
    }
}
