package com.example.shopsphere.data.order

import com.example.shopsphere.data.Address
import com.example.shopsphere.data.CartProduct
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random.Default.nextLong

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val products: List<CartProduct>,
    val address: Address,
    val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(Date()),
    val orderId: Long = nextLong(0, 100000000000) + totalPrice.toLong()
){
    constructor() : this("",0f, emptyList(),Address())
}
