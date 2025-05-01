package com.example.mrbugger_app.state

import com.example.mrbugger_app.model.CategoryModel

sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val categories: List<CategoryModel>, val isOffline: Boolean) : CategoryUiState()
    data class Error(val message: String, val isOffline: Boolean) : CategoryUiState()
}
