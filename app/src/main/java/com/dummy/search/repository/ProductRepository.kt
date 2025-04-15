package com.dummy.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dummy.search.model.Product
import com.dummy.search.network.ApiService
import com.dummy.search.paging.ProductPagingSource
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val apiService: ApiService) {

    fun getPagedProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(apiService) }
        ).flow
    }

    // Optional: Keep your single product fetch if needed
    suspend fun getProductDetails(productId: Int): Product {
        return apiService.getProductDetails(productId)
    }
}
