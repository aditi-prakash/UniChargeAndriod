package com.example.unichargeandroid.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.unichargeandroid.data.model.User
import com.google.gson.Gson

object UserManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_DATA = "user_data"

    private var sharedPreferences: SharedPreferences? = null
    private val gson = Gson()

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences?.edit()?.putString(KEY_USER_DATA, userJson)?.apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences?.getString(KEY_USER_DATA, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearUser() {
        sharedPreferences?.edit()?.remove(KEY_USER_DATA)?.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getUser() != null && TokenManager.getToken() != null
    }
}