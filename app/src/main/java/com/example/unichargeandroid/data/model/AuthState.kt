package com.example.unichargeandroid.data.model

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}