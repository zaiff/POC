package com.dummy.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dummy.search.database.CartDatabase
import com.dummy.search.nav.setupNavGraph
import com.dummy.search.network.ApiService
import com.dummy.search.repository.CartRepository
import com.dummy.search.repository.ProductRepository
import com.dummy.search.ui.theme.MyApplicationTheme
import com.dummy.search.viewmodel.CartViewModel
import com.dummy.search.viewmodel.CartViewModelFactory
import com.dummy.search.viewmodel.ProductViewModel
import com.dummy.search.viewmodel.ProductViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create API and Repositories
        val apiService = ApiService.create()
        val productRepository = ProductRepository(apiService)
        val cartDao = CartDatabase.getDatabase(applicationContext).cartDao()
        val cartRepository = CartRepository(cartDao)

        // Create ViewModels using factories
        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(productRepository)
        )[ProductViewModel::class.java]

        cartViewModel = ViewModelProvider(
            this,
            CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                // ✅ Set status bar color to match TopAppBar
                val primaryColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                SideEffect {
                    window.statusBarColor = primaryColor.toArgb()
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = false // ✅ light content (white icons)
                }

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    setupNavGraph(
                        navController = navController,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}
