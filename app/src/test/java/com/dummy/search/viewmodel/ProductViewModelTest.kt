package com.dummy.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.dummy.search.model.Product
import com.dummy.search.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: ProductRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()

        // ✅ Mock ProductDetails
        val mockProduct = Product(
            id = 1,
            title = "Mock Product 1",
            category = "Electronics",
            price = 199.0,
            discountPercentage = 10.0,
            description = "A test product",
            thumbnail = "thumb1.png",
            rating = 4.5
        )
        coEvery { repository.getProductDetails(1) } returns mockProduct

        // ✅ Mock getPagedProducts() with no arguments
        val fakeProducts = listOf(
            mockProduct,
            Product(
                id = 2,
                title = "Mock Product 2",
                category = "Books",
                price = 299.0,
                discountPercentage = 15.0,
                description = "Another test product",
                thumbnail = "thumb2.png",
                rating = 4.7
            )
        )
        val fakePagingData = PagingData.from(fakeProducts)
        coEvery { repository.getPagedProducts() } returns flowOf(fakePagingData)

        viewModel = ProductViewModel(repository)
    }

    @Test
    fun `fetchProductDetails sets product correctly`() = runTest {
        viewModel.fetchProductDetails(1)
        advanceUntilIdle()

        val product = viewModel.productDetails.value
        assertNotNull(product)
        assertEquals(1, product?.id)
        assertEquals("Mock Product 1", product?.title)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
