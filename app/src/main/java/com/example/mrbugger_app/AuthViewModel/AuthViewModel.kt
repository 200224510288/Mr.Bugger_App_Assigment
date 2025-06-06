package com.example.mrbugger_app.AuthViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    // Firebase authentication instance
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    // LiveData to observe authentication state changes
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // LiveData to track if user has previously logged in successfully
    private val _hasPreviousLogin = MutableLiveData<Boolean>()
    val hasPreviousLogin: LiveData<Boolean> = _hasPreviousLogin

    // LiveData to track if biometric authentication is required
    private val _requiresBiometricAuth = MutableLiveData<Boolean>()
    val requiresBiometricAuth: LiveData<Boolean> = _requiresBiometricAuth

    // Check authentication status when ViewModel is initialized
    init {
        checkAuthStatus()
    }

    // Updates authState accordingly and checks for previous login
    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _authState.value = AuthState.unauthenticated
            _hasPreviousLogin.value = false
            _requiresBiometricAuth.value = false
        } else {
            // User is authenticated with Firebase but needs biometric verification
            _authState.value = AuthState.RequiresBiometric
            _hasPreviousLogin.value = true
            _requiresBiometricAuth.value = true
        }
    }

    // Set previous login status (call this after successful login)
    fun setPreviousLoginStatus(context: Context, hasLoggedIn: Boolean) {
        val sharedPrefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("has_previous_login", hasLoggedIn).apply()
        _hasPreviousLogin.value = hasLoggedIn
    }

    // Check if user has previously logged in
    fun checkPreviousLoginStatus(context: Context) {
        val sharedPrefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val hasLoggedIn = sharedPrefs.getBoolean("has_previous_login", false)
        _hasPreviousLogin.value = hasLoggedIn

        // If user has previously logged in and Firebase user exists, require biometric
        if (hasLoggedIn && auth.currentUser != null) {
            _requiresBiometricAuth.value = true
            _authState.value = AuthState.RequiresBiometric
        }
    }

    // Biometric login - authenticate existing user
    fun biometricLogin() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated
            _requiresBiometricAuth.value = false
        } else {
            _authState.value = AuthState.Error("No authenticated user found")
        }
    }

    // Manual login with email and password
    fun login(email: String, password: String, context: Context? = null) {
        // Validate input fields
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }

        // Set loading state
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    _requiresBiometricAuth.value = false
                    // Save that user has successfully logged in
                    context?.let { setPreviousLoginStatus(it, true) }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String, context: Context? = null) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    _requiresBiometricAuth.value = false
                    // Save that user has successfully logged in
                    context?.let { setPreviousLoginStatus(it, true) }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signout(navController: NavController, context: Context? = null){
        auth.signOut()
        _authState.value = AuthState.unauthenticated
        _requiresBiometricAuth.value = false
        // Clear previous login status
        context?.let { setPreviousLoginStatus(it, false) }
        navController.navigate("LoginScreen")
    }

    // Force biometric authentication requirement
    fun requireBiometricAuth() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.RequiresBiometric
            _requiresBiometricAuth.value = true
        }
    }
}

/**
 * Sealed class representing different authentication states.
 */
sealed class AuthState{
    object Authenticated : AuthState()
    object unauthenticated : AuthState()
    object Loading : AuthState()
    object RequiresBiometric : AuthState()  // New state for biometric requirement
    data class Error(val message : String) : AuthState()
}