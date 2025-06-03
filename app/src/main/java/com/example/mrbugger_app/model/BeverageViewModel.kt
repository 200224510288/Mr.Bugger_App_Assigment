package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mrbugger_app.Repository.BeverageRepository
import com.example.mrbugger_app.state.BeverageUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeverageViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<BeverageUiState>(BeverageUiState.Loading)
    val uiState: StateFlow<BeverageUiState> = _uiState

    private val repository = BeverageRepository(application)

    init {
        loadBeverages()
    }

    fun refreshData() {
        loadBeverages()
    }

    fun loadBeverages() {
        viewModelScope.launch {
            _uiState.value = BeverageUiState.Loading
            delay(2000)
            val isOnline = isNetworkAvailable()

            if (isOnline) {
                // Try to load from remote first when online
                try {
                    val result = repository.loadBeveragesRemote(true)
                    val beverages = result.getOrNull()

                    if (beverages != null && beverages.isNotEmpty()) {
                        _uiState.value = BeverageUiState.Success(
                            beverages = beverages,
                            isOffline = false
                        )
                        return@launch
                    }
                } catch (e: Exception) {
                    // Log error but continue to try local data
                    android.util.Log.w("BeverageViewModel", "Remote loading failed, trying local", e)
                }

                // If remote fails while online, fallback to local
                loadLocalBeverages(isOffline = false)
            } else {
                // Device is offline, load from local assets directly
                loadLocalBeverages(isOffline = true)
            }
        }
    }

    private fun loadLocalBeverages(isOffline: Boolean) {
        try {
            val localBeverages = repository.loadLocalBeverages()
            if (localBeverages.isNotEmpty()) {
                _uiState.value = BeverageUiState.Success(
                    beverages = localBeverages,
                    isOffline = isOffline
                )
            } else {
                _uiState.value = BeverageUiState.Error(
                    message = "No beverages available. Please check your assets folder.",
                    isOffline = isOffline
                )
            }
        } catch (e: Exception) {
            _uiState.value = BeverageUiState.Error(
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

            android.util.Log.d("BeverageViewModel", "Network available: $hasInternet")
            hasInternet
        } catch (e: Exception) {
            android.util.Log.e("BeverageViewModel", "Error checking network availability", e)
            false
        }
    }
}

// BeverageViewModelFactory.kt
class BeverageViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeverageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeverageViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}