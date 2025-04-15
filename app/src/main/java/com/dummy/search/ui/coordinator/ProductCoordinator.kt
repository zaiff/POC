
package com.dummy.search.ui.coordinator

import androidx.navigation.NavController

class ProductCoordinator(private val navController: NavController) {
    fun goToDetails(productId: Int) {
        navController.navigate("product_details/$productId")
    }

    fun goToCart() {
        navController.navigate("cart")
    }

    fun goBack() {
        navController.popBackStack()
    }
}
