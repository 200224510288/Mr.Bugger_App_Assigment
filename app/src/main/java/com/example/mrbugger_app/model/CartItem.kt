package com.example.mrbugger_app.model

class CartItem (
    val imageRes: Int,
    val imageUrl: String? = null,
    val name: String,
    val price: Double,
    val quantity: Int
)