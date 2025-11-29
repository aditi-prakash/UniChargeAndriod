package com.example.unichargeandroid.data.repository

import com.example.unichargeandroid.data.model.AuthResponse
import com.example.unichargeandroid.data.model.LoginRequest
import com.example.unichargeandroid.data.model.SignupRequest
import com.example.unichargeandroid.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {
    private val authApiService = RetrofitClient.authApiService

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                println("DEBUG: Attempting login for email: $email")
                val response = authApiService.login(LoginRequest(email, password))
                println("DEBUG: Login response code: ${response.code()}")

                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        println("DEBUG: Login successful, token: ${authResponse.token}")
                        println("DEBUG: User data received: ${authResponse.user}")
                        Result.success(authResponse)
                    } else {
                        println("DEBUG: Login failed - empty response body")
                        Result.failure(Exception("Empty response from server"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("DEBUG: Login HTTP error: ${response.code()} - $errorBody")
                    Result.failure(Exception("Login failed: ${response.code()} - $errorBody"))
                }
            } catch (e: HttpException) {
                println("DEBUG: Login HTTP exception: ${e.message}")
                Result.failure(Exception("Server error: ${e.message}"))
            } catch (e: IOException) {
                println("DEBUG: Login network error: ${e.message}")
                Result.failure(Exception("Network error: Please check your connection"))
            } catch (e: Exception) {
                println("DEBUG: Login unexpected error: ${e.message}")
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }
    }

    suspend fun register(fullName: String, email: String, phoneNumber: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                println("DEBUG: Attempting registration for email: $email")
                val response = authApiService.register(SignupRequest(fullName, email, phoneNumber, password))
                println("DEBUG: Registration response code: ${response.code()}")

                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        println("DEBUG: Registration successful, token: ${authResponse.token}")
                        println("DEBUG: User data received: ${authResponse.user}")
                        Result.success(authResponse)
                    } else {
                        println("DEBUG: Registration failed - empty response body")
                        Result.failure(Exception("Empty response from server"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("DEBUG: Registration HTTP error: ${response.code()} - $errorBody")
                    Result.failure(Exception("Registration failed: ${response.code()} - $errorBody"))
                }
            } catch (e: HttpException) {
                println("DEBUG: Registration HTTP exception: ${e.message}")
                Result.failure(Exception("Server error: ${e.message}"))
            } catch (e: IOException) {
                println("DEBUG: Registration network error: ${e.message}")
                Result.failure(Exception("Network error: Please check your connection"))
            } catch (e: Exception) {
                println("DEBUG: Registration unexpected error: ${e.message}")
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }
    }
}