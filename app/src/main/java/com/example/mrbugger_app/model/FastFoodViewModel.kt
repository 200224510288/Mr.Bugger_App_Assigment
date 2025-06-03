package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.Repository.FastFoodsRepository
import com.example.mrbugger_app.state.FastFoodUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FastFoodViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<FastFoodUiState>(FastFoodUiState.Loading)
    val uiState: StateFlow<FastFoodUiState> = _uiState

    private val repository = FastFoodsRepository(application)

    init {
        loadFastFoods()
    }

    fun refreshData() {
        loadFastFoods()
    }

    fun loadFastFoods() {
        viewModelScope.launch {
            _uiState.value = FastFoodUiState.Loading
            delay(2000)
            val isOnline = isNetworkAvailable()

            if (isOnline) {
                // Try to load from remote first when online
                try {
                    val result = repository.loadFastFoodsRemote(true)
                    val fastFoods = result.getOrNull()

                    if (fastFoods != null && fastFoods.isNotEmpty()) {
                        _uiState.value = FastFoodUiState.Success(
                            fastfoods = fastFoods,
                            isOffline = false
                        )
                        return@launch
                    }
                } catch (e: Exception) {
                    // Log error but continue to try local data
                    android.util.Log.w("FastFoodViewModel", "Remote loading failed, trying local", e)
                }

                // If remote fails while online, fallback to local
                loadLocalFastFoods(isOffline = false)
            } else {
                // Device is offline, load from local assets directly
                loadLocalFastFoods(isOffline = true)
            }
        }
    }

    private fun loadLocalFastFoods(isOffline: Boolean) {
        try {
            val localFastFoods = repository.loadLocalFastFoods()
            if (localFastFoods.isNotEmpty()) {
                _uiState.value = FastFoodUiState.Success(
                    fastfoods = localFastFoods,
                    isOffline = isOffline
                )
            } else {
                _uiState.value = FastFoodUiState.Error(
                    message = "No fast foods available. Please check your assets folder.",
                    isOffline = isOffline
                )
            }
        } catch (e: Exception) {
            _uiState.value = FastFoodUiState.Error(
                message = "Failed to load local data: ${e.localizedMessage ?: "Unknown error"}",
                isOffline = isOffline
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return try {
            val connectivityManager = getApplication<Application>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val network = connectivityManager?.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            val hasInternet = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

            android.util.Log.d("FastFoodViewModel", "Network available: $hasInternet")
            hasInternet
        } catch (e: Exception) {
            android.util.Log.e("FastFoodViewModel", "Error checking network availability", e)
            false
        }
    }
}

// FastFoodViewModelFactory.kt
class FastFoodViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FastFoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FastFoodViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}