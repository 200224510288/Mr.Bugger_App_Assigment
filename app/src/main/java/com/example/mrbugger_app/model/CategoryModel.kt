package com.example.mrbugger_app.model

// CategoryModel.kt
data class CategoryModel(
    val id: Int,
    val name: String,
    val imageUrl: String
)

// CategoryResponse.kt - for parsing the JSON response
data class CategoryResponse(
    val categories: List<CategoryModel>
)