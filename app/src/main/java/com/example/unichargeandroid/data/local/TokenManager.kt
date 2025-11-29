package com.example.unichargeandroid.data.local

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val TOKEN_KEY = "auth_token"
    private const val PREFS_NAME = "auth_prefs"

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        sharedPreferences?.edit()?.putString(TOKEN_KEY, token)?.apply()
    }

    fun getToken(): String? {
        return sharedPreferences?.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences?.edit()?.remove(TOKEN_KEY)?.apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}