package com.example.mrbugger_app.state

import com.example.mrbugger_app.model.FastFoodModel

sealed class FastFoodUiState {
    object Loading : FastFoodUiState()
    data class Success(val fastfoods: List<FastFoodModel>, val isOffline: Boolean) : FastFoodUiState()
    data class Error(val message: String, val isOffline: Boolean) : FastFoodUiState()
}