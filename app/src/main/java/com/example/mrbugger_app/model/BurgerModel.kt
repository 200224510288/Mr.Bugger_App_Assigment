package com.example.mrbugger_app.model

// BurgerModel.kt
data class BurgerModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val categoryImageUrl: String,
    val category: String = "burger" // veg or nonveg
)
