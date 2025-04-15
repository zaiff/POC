package com.dummy.search.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dummy.search.ui.coordinator.ProductCoordinator
import com.dummy.search.ui.screens.CartScreen
import com.dummy.search.ui.screens.HomeScreen
import com.dummy.search.ui.screens.ProductDetailsScreen
import com.dummy.search.viewmodel.CartViewModel
import com.dummy.search.viewmodel.ProductViewModel

fun NavGraphBuilder.setupNavGraph(
    navController: NavController,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val coordinator = ProductCoordinator(navController)

    composable("home") {
        HomeScreen(
            productViewModel = productViewModel,
            cartViewModel = cartViewModel,
            productCoordinator = coordinator
        )
    }

    composable("cart") {
        CartScreen(
            cartViewModel = cartViewModel,
            productCoordinator = coordinator
        )
    }

    composable("product_details/{productId}") { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
        ProductDetailsScreen(
            productId = productId,
            productCoordinator = coordinator,
            productViewModel = productViewModel,
            cartViewModel = cartViewModel
        )
    }
}
