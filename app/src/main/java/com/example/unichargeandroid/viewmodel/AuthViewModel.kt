package com.example.unichargeandroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unichargeandroid.data.local.TokenManager
import com.example.unichargeandroid.data.local.UserManager
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.data.model.User
import com.example.unichargeandroid.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    private val _signupState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signupState: StateFlow<AuthState> = _signupState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            val token = TokenManager.getToken()
            val user = UserManager.getUser()
            println("DEBUG: Checking auth state, token exists: ${token != null}, user exists: ${user != null}")

            _authState.value = if (token != null && user != null) {
                _currentUser.value = user
                AuthState.Authenticated
            } else {
                // Clear both if one is missing
                if (token != null) TokenManager.clearToken()
                if (user != null) UserManager.clearUser()
                AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        println("DEBUG: Login called with email: $email")
        _loginState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                val authResponse = result.getOrNull()
                if (authResponse?.token != null && authResponse.user != null) {
                    println("DEBUG: Login successful, saving token and user data")
                    TokenManager.saveToken(authResponse.token)
                    UserManager.saveUser(authResponse.user)
                    _currentUser.value = authResponse.user
                    _loginState.value = AuthState.Authenticated
                    _authState.value = AuthState.Authenticated
                } else {
                    println("DEBUG: Login failed - no token or user data in response")
                    _loginState.value = AuthState.Error("Login failed: No token or user data received from server")
                }
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown login error"
                println("DEBUG: Login error: $errorMessage")
                _loginState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun register(fullName: String, email: String, phoneNumber: String, password: String) {
        println("DEBUG: Register called with email: $email")
        _signupState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.register(fullName, email, phoneNumber, password)
            if (result.isSuccess) {
                val authResponse = result.getOrNull()
                if (authResponse?.token != null && authResponse.user != null) {
                    println("DEBUG: Registration successful, saving token and user data")
                    TokenManager.saveToken(authResponse.token)
                    UserManager.saveUser(authResponse.user)
                    _currentUser.value = authResponse.user
                    _signupState.value = AuthState.Success
                    _authState.value = AuthState.Authenticated
                } else {
                    println("DEBUG: Registration failed - no token or user data in response")
                    _signupState.value = AuthState.Error("Registration completed but no authentication data received")
                }
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown registration error"
                println("DEBUG: Registration error: $errorMessage")
                _signupState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun setAuthenticated(token: String, user: User) {
        println("DEBUG: Manually setting authenticated with token and user")
        TokenManager.saveToken(token)
        UserManager.saveUser(user)
        _currentUser.value = user
        _authState.value = AuthState.Authenticated
        _loginState.value = AuthState.Authenticated
    }

    fun setUnauthenticated() {
        println("DEBUG: Setting unauthenticated - clearing token and user data")
        TokenManager.clearToken()
        UserManager.clearUser()
        _currentUser.value = null
        _authState.value = AuthState.Unauthenticated
        _loginState.value = AuthState.Idle
        _signupState.value = AuthState.Idle
    }

    fun updateUser(user: User) {
        println("DEBUG: Updating user data")
        UserManager.saveUser(user)
        _currentUser.value = user
    }

    fun logout() {
        setUnauthenticated()
    }

    fun resetLoginState() {
        _loginState.value = AuthState.Idle
    }

    fun resetSignupState() {
        _signupState.value = AuthState.Idle
    }

    fun getStoredUser(): User? {
        return UserManager.getUser()
    }
}