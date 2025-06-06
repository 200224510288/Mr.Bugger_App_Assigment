package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    // SharedPreferences for theme persistence
    private val sharedPreferences = getApplication<Application>()
        .getSharedPreferences("app_theme", Context.MODE_PRIVATE)

    // Sensor manager and light sensor
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val lightSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    // Theme state
    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    // Ambient light sensor states
    private val _isAmbientLightEnabled = MutableStateFlow(false)
    val isAmbientLightEnabled: StateFlow<Boolean> = _isAmbientLightEnabled.asStateFlow()

    private val _currentLightLevel = MutableStateFlow(0f)
    val currentLightLevel: StateFlow<Float> = _currentLightLevel.asStateFlow()

    private val _darkModeThreshold = MutableStateFlow(50f) // Default threshold in lux
    val darkModeThreshold: StateFlow<Float> = _darkModeThreshold.asStateFlow()

    private val _showPermissionDialog = MutableStateFlow(false)
    val showPermissionDialog: StateFlow<Boolean> = _showPermissionDialog.asStateFlow()

    // Theme mode enum
    enum class ThemeMode {
        MANUAL, AMBIENT_LIGHT
    }

    private val _themeMode = MutableStateFlow(ThemeMode.MANUAL)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        loadThemePreferences()
    }

    // Load all theme preferences from SharedPreferences
    private fun loadThemePreferences() {
        _isDarkMode.value = sharedPreferences.getBoolean("is_dark_mode", false)
        _isAmbientLightEnabled.value = sharedPreferences.getBoolean("ambient_light_enabled", false)
        _darkModeThreshold.value = sharedPreferences.getFloat("dark_mode_threshold", 50f)

        val themeModeString = sharedPreferences.getString("theme_mode", ThemeMode.MANUAL.name)
        _themeMode.value = ThemeMode.valueOf(themeModeString ?: ThemeMode.MANUAL.name)

        // Start sensor if ambient light was previously enabled
        if (_isAmbientLightEnabled.value) {
            startAmbientLightSensor()
        }
    }

    // Save theme preference to SharedPreferences
    private fun saveThemePreference(key: String, value: Any) {
        val editor = sharedPreferences.edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is String -> editor.putString(key, value)
        }
        editor.apply()
    }

    // Manual theme toggle
    fun toggleTheme() {
        if (_themeMode.value == ThemeMode.MANUAL) {
            val newTheme = !_isDarkMode.value
            _isDarkMode.value = newTheme
            saveThemePreference("is_dark_mode", newTheme)
        }
    }

    // Manual theme setting
    fun setTheme(isDark: Boolean) {
        if (_themeMode.value == ThemeMode.MANUAL) {
            _isDarkMode.value = isDark
            saveThemePreference("is_dark_mode", isDark)
        }
    }

    // Request ambient light sensor permission
    fun requestAmbientLightPermission() {
        if (lightSensor != null) {
            _showPermissionDialog.value = true
        }
    }

    // Handle permission dialog response
    fun onPermissionDialogResponse(granted: Boolean) {
        _showPermissionDialog.value = false
        if (granted) {
            enableAmbientLight(true)
        }
    }

    // Enable/disable ambient light sensor
    fun enableAmbientLight(enable: Boolean) {
        if (enable && lightSensor == null) {
            // Device doesn't have ambient light sensor
            return
        }

        _isAmbientLightEnabled.value = enable
        saveThemePreference("ambient_light_enabled", enable)

        if (enable) {
            _themeMode.value = ThemeMode.AMBIENT_LIGHT
            saveThemePreference("theme_mode", ThemeMode.AMBIENT_LIGHT.name)
            startAmbientLightSensor()
        } else {
            _themeMode.value = ThemeMode.MANUAL
            saveThemePreference("theme_mode", ThemeMode.MANUAL.name)
            stopAmbientLightSensor()
        }
    }

    // Start ambient light sensor
    private fun startAmbientLightSensor() {
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Stop ambient light sensor
    private fun stopAmbientLightSensor() {
        sensorManager.unregisterListener(this)
    }

    // Update dark mode threshold
    fun updateDarkModeThreshold(threshold: Float) {
        _darkModeThreshold.value = threshold
        saveThemePreference("dark_mode_threshold", threshold)

        // Immediately apply new threshold to current light level
        if (_isAmbientLightEnabled.value) {
            updateThemeBasedOnLight(_currentLightLevel.value)
        }
    }

    // Check if device has ambient light sensor
    fun hasAmbientLightSensor(): Boolean {
        return lightSensor != null
    }

    // SensorEventListener implementation
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LIGHT && _isAmbientLightEnabled.value) {
                val lux = it.values[0]
                _currentLightLevel.value = lux
                updateThemeBasedOnLight(lux)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this implementation
    }

    // Update theme based on light level
    private fun updateThemeBasedOnLight(lux: Float) {
        val shouldBeDark = lux < _darkModeThreshold.value
        if (_isDarkMode.value != shouldBeDark) {
            _isDarkMode.value = shouldBeDark
            // Don't save to SharedPreferences for ambient light changes
            // as this should be dynamic
        }
    }

    // Clean up sensor listener
    override fun onCleared() {
        super.onCleared()
        stopAmbientLightSensor()
    }
}