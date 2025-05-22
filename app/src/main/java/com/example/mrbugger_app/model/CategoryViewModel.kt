package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.Data.Category
import com.example.mrbugger_app.state.CategoryUiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            val isOnline = isNetworkAvailable()

            try {
                val result = Category.loadCategoryRemote(isOnline)
                val categories = result.getOrNull().orEmpty()

                if (categories.isNotEmpty()) {
                    _uiState.value = CategoryUiState.Success(
                        categories = categories,
                        isOffline = !isOnline
                    )
                    return@launch
                }

                // fallback to local data
                val localCategories = loadLocalData()
                if (localCategories.isNotEmpty()) {
                    _uiState.value = CategoryUiState.Success(
                        categories = localCategories,
                        isOffline = true
                    )
                } else {
                    _uiState.value = CategoryUiState.Error(
                        message = "No categories available.",
                        isOffline = true
                    )
                }

            } catch (e: Exception) {
                val fallbackCategories = loadLocalData()
                if (fallbackCategories.isNotEmpty()) {
                    _uiState.value = CategoryUiState.Success(
                        categories = fallbackCategories,
                        isOffline = true
                    )
                } else {
                    _uiState.value = CategoryUiState.Error(
                        message = e.localizedMessage ?: "Unexpected error.",
                        isOffline = true
                    )
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    private fun loadLocalData(): List<CategoryModel> {
        return try {
            val assetManager = getApplication<Application>().assets
            val inputStream = assetManager.open("categories.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<CategoryModel>>() {}.type
            val data = Gson().fromJson<List<CategoryModel>>(reader, type)
            android.util.Log.d("CategoryViewModel", "Local data loaded: $data")
            data
        } catch (e: Exception) {
            android.util.Log.e("CategoryViewModel", "Failed to load local data", e)
            emptyList()
        }
    }

}
