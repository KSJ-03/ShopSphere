package com.example.shopsphere.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shopsphere.R
import com.example.shopsphere.databinding.ActivityShoppingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private lateinit var ShoppingNavController: NavController

    private val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val ShoppingNavHostFragment = supportFragmentManager
            .findFragmentById(R.id.Shopping_nav_host_fragment) as NavHostFragment
        ShoppingNavController = ShoppingNavHostFragment.navController
    }
}