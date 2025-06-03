package com.example.mrbugger_app.state

import com.example.mrbugger_app.model.BeverageModel

sealed class BeverageUiState {
    object Loading : BeverageUiState()
    data class Success(val beverages: List<BeverageModel>, val isOffline: Boolean) : BeverageUiState()
    data class Error(val message: String, val isOffline: Boolean) : BeverageUiState()
}