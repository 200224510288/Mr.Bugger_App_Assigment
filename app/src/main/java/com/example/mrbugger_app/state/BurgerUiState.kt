package com.example.mrbugger_app.state

import com.example.mrbugger_app.model.BurgerModel

sealed class BurgerUiState {
    object Loading : BurgerUiState()
    data class Success(val burgers: List<BurgerModel>, val isOffline: Boolean) : BurgerUiState()
    data class Error(val message: String, val isOffline: Boolean) : BurgerUiState()
}