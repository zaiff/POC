
package com.dummy.search.viewmodel

import com.dummy.search.model.Product
import com.dummy.search.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductViewModel()
    }

    @Test
    fun `fetch initial products returns data`() = runTest {
        viewModel.fetchProducts()
        advanceUntilIdle()
        val products = viewModel.products.value
        assertTrue(products.isNotEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
