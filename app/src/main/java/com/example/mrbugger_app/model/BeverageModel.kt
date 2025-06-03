package com.example.mrbugger_app.model

data class BeverageModel (
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val categoryImageUrl: String,
    val category: String = "beverage"
)