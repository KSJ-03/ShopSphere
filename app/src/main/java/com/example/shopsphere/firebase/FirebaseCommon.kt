package com.example.shopsphere.firebase

import android.util.Log
import com.example.shopsphere.data.CartProduct
import com.example.shopsphere.util.evalProductId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import javax.inject.Inject

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val cartCollection =
        firestore.collection("user").document(auth.uid!!).collection("cart")

    fun addProductToCart(cartProduct: CartProduct, onResult: (CartProduct?, Exception?) -> Unit) {
        cartCollection.document(
            evalProductId(
                cartProduct.product.id,
                cartProduct.selectedSize,
                cartProduct.selectedColour
            )
        ).set(cartProduct)
            .addOnSuccessListener {
                onResult(cartProduct, null)
            }
            .addOnFailureListener {
                onResult(null, it)
            }
    }

    fun increaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        // .runBatch - same as runTransaction but allows only read
        firestore.runTransaction { transaction -> // if any step fails then the whole thing fails...atomicity. Allows read and write
            val documentRef = cartCollection.document(documentId)
            val document = transaction.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let { cartProduct ->
                val newQuantity = cartProduct.quantity + 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transaction.set(documentRef, newProductObject)
            }
        }
            .addOnSuccessListener {
                onResult(documentId, null)
                Log.d("increaseQuantity", "Product Quantity increased")
            }
            .addOnFailureListener {
                onResult(null, it)
                Log.d("DetailsVM", "$it")
            }
    }

    fun decreaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        // .runBatch - same as runTransaction but allows only read
        firestore.runTransaction { transaction -> // if any step fails then the whole thing fails...atomicity. Allows read and write
            val documentRef = cartCollection.document(documentId)
            val document = transaction.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let { cartProduct ->
                val newQuantity = cartProduct.quantity - 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transaction.set(documentRef, newProductObject)
            }
        }
            .addOnSuccessListener {
                onResult(documentId, null)
                Log.d("increaseQuantity", "Product Quantity increased")
            }
            .addOnFailureListener {
                onResult(null, it)
                Log.d("DetailsVM", "$it")
            }
    }

    enum class QuantityChanging{
        INCREASE,
        DECREASE
    }
}