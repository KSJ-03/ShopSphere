package com.example.shopsphere.data.order

import com.example.shopsphere.data.Address
import com.example.shopsphere.data.CartProduct

data class Order(
    val orderStatus:String,
    val totalPrice:Float,
    val products: List<CartProduct>,
    val address: Address
)
