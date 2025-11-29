package com.example.unichargeandroid.data.model

import android.app.Notification
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("role") val role: String,
    @SerializedName("authProvider") val authProvider: String,
    @SerializedName("wallet") val wallet: Wallet,
    @SerializedName("location") val location: Location,
    @SerializedName("preferences") val preferences: Preferences,
    @SerializedName("vehicles") val vehicles: List<Vehicle> = emptyList(),
    @SerializedName("paymentMethods") val paymentMethods: List<PaymentMethod> = emptyList(),
    @SerializedName("paymentHistory") val paymentHistory: List<PaymentHistory> = emptyList(),
    @SerializedName("notifications") val notifications: List<Notification> = emptyList(),
    @SerializedName("bookings") val bookings: List<String> = emptyList(),
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

data class Wallet(
    @SerializedName("balance") val balance: Double,
    @SerializedName("loyaltyPoints") val loyaltyPoints: Int,
    @SerializedName("defaultPaymentMethod") val defaultPaymentMethod: String? = null
)

data class Location(
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("coordinates") val coordinates: Coordinates? = null,
    @SerializedName("isProvided") val isProvided: Boolean = false
)

data class Coordinates(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

data class Preferences(
    @SerializedName("preferredLanguage") val preferredLanguage: String = "en",
    @SerializedName("notificationsEnabled") val notificationsEnabled: Boolean = true,
    @SerializedName("renewablePriority") val renewablePriority: Boolean = false
)

data class Vehicle(
    @SerializedName("_id") val id: String,
    @SerializedName("make") val make: String,
    @SerializedName("model") val model: String,
    @SerializedName("batteryCapacityKwh") val batteryCapacityKwh: Int,
    @SerializedName("preferredConnector") val preferredConnector: String,
    @SerializedName("createdAt") val createdAt: String
)

data class PaymentHistory(
    @SerializedName("_id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("method") val method: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("referenceId") val referenceId: String? = null,
    @SerializedName("status") val status: String,
    @SerializedName("vehicleName") val vehicleName: String? = null,
    @SerializedName("stationName") val stationName: String? = null,
    @SerializedName("createdAt") val createdAt: String
)

data class Notification(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String,
    @SerializedName("type") val type: String,
    @SerializedName("isRead") val isRead: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("expiresAt") val expiresAt: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class SignupRequest(
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("password") val password: String
)

data class AuthResponse(
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: User? = null,
)

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: String? = null
)