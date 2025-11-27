package com.example.unichargeandroid.data.model

data class WalletData(
    val balance: Double = 400.50,
    val loyaltyPoints: Int = 350,
    val defaultPaymentMethod: String? = null
)

data class PaymentMethod(
    val id: String,
    val type: String, // "upi" or "card"
    val upiId: String? = null,
    val card: CardDetails? = null,
    val isDefault: Boolean = false
)

data class CardDetails(
    val cardNumberMasked: String,
    val cardHolder: String,
    val expiryMonth: String,
    val expiryYear: String
)

// Form Data class
data class FormData(
    val type: String,
    val upiId: String,
    val card: CardDetails,
    val isDefault: Boolean
)