package com.example.shopsphere.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shopsphere.R
import com.example.shopsphere.databinding.ActivityShoppingBinding
import com.example.shopsphere.util.Resource
import com.example.shopsphere.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private lateinit var ShoppingNavController: NavController

    private val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }

    val viewmodel by viewModels<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val ShoppingNavHostFragment = supportFragmentManager
            .findFragmentById(R.id.Shopping_nav_host_fragment) as NavHostFragment
        ShoppingNavController = ShoppingNavHostFragment.navController

        binding.bottomNavigation.setupWithNavController(ShoppingNavController)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.cartProducts.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            val count = it.data?.size ?: 0
                            val bottomNavigation = binding.bottomNavigation
                            bottomNavigation.getOrCreateBadge(R.id.cartFragment).apply {
                                number = count
                                backgroundColor = resources.getColor(R.color.g_blue, null)
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}