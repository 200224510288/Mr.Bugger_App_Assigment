package com.example.mrbugger_app.Data

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories") val categories: List<Category2>
)

data class Category2(
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("name") val name: String
)