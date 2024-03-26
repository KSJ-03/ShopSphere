package com.example.shopsphere.data

data class CartProduct(
    val product:Product,
    val quantity:Int,
    val selectedColour:Int? = null,
    val selectedSize:String? = null
){
    constructor():this(Product(),1,null,null)
}
