package com.example.mrbugger_app.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.state.CategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading

            val isNetworkAvailable = isNetworkAvailable()
            try {
                val result = Category.loadCategoryRemote(isNetworkAvailable)
                if (result.isSuccess) {
                    val categories = result.getOrNull().orEmpty()

                    if (categories.isNotEmpty()) {
                        _uiState.value = CategoryUiState.Success(
                            categories = categories,
                            isOffline = !isNetworkAvailable
                        )
                    } else {
                        _uiState.value = CategoryUiState.Error(
                            message = "No categories available.",
                            isOffline = !isNetworkAvailable
                        )
                    }
                } else {
                    _uiState.value = CategoryUiState.Error(
                        message = result.exceptionOrNull()?.localizedMessage ?: "Failed to load categories.",
                        isOffline = !isNetworkAvailable
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(
                    message = e.localizedMessage ?: "Unexpected error.",
                    isOffline = !isNetworkAvailable
                )
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.let {
            val network = it.activeNetwork ?: return false
            val capabilities = it.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } ?: false
    }
}


class CategoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}