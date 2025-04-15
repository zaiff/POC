package com.dummy.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dummy.search.model.Product
import com.dummy.search.network.ApiService

class ProductPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 0
            val limit = params.loadSize

            val response = apiService.getProducts(limit = limit, skip = currentPage)
            val products = response.products

            LoadResult.Page(
                data = products,
                prevKey = if (currentPage == 0) null else currentPage - limit,
                nextKey = if (products.isEmpty()) null else currentPage + limit
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }
}
