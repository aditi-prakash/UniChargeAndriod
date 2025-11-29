package com.example.unichargeandroid.data.network

import com.example.unichargeandroid.data.model.AuthResponse
import com.example.unichargeandroid.data.model.LoginRequest
import com.example.unichargeandroid.data.model.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/users/register")
    suspend fun register(@Body request: SignupRequest): Response<AuthResponse>

    @GET("api/users/google")
    suspend fun googleAuth(): Response<AuthResponse>
}

