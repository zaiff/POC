
package com.dummy.search.viewmodel

import com.dummy.search.model.CartItem
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @Test
    fun `add to cart updates cart items`() = runTest {
        val cartItem = CartItem(1, "Sample Product", 100.0, "image.png")
        viewModel.addToCart(cartItem)
        advanceUntilIdle()
        assertTrue(viewModel.cartItems.value.contains(cartItem))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
