package com.example.mrbugger_app.model

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("imageRes")
    val imageRes: Int,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("quantity")
    val quantity: Int
) {
    // Helper method to get total price for this item
    fun getTotalPrice(): Double = price * quantity
}