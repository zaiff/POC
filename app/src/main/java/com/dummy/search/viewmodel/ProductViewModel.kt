package com.dummy.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dummy.search.model.Product
import com.dummy.search.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    // Paging
    val pagedProducts: Flow<PagingData<Product>> = repository
        .getPagedProducts()
        .cachedIn(viewModelScope)

    // Optional: Keep if ProductDetailsScreen relies on flow state
    private val _productDetails = MutableStateFlow<Product?>(null)
    val productDetails: StateFlow<Product?> = _productDetails

    fun fetchProductDetails(productId: Int) {
        viewModelScope.launch {
            _productDetails.value = null
            try {
                _productDetails.value = repository.getProductDetails(productId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
