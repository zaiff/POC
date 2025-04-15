package com.dummy.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dummy.search.model.CartItem
import com.dummy.search.model.Product
import com.dummy.search.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var repository: CartRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = CartViewModel(repository)
    }

    @Test
    fun `addToCart should convert Product to CartItem and call repository`() = runTest {
        val product = Product(
            id = 1,
            title = "Mock Product",
            category = "Category",
            price = 123.0,
            description = "desc",
            thumbnail = "mock.png",
            rating = 4.5,
            discountPercentage = 10.0
        )

        viewModel.addToCart(product)
        advanceUntilIdle()

        coVerify {
            repository.addToCart(
                match<CartItem> {
                    it.id == product.id &&
                            it.title == product.title &&
                            it.price == product.price &&
                            it.thumbnail == product.thumbnail
                }
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
