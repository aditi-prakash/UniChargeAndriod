package com.example.unichargeandroid.data.model

import com.google.gson.annotations.SerializedName

data class WalletData(
    val balance: Double = 400.50,
    val loyaltyPoints: Int = 350,
    val defaultPaymentMethod: String? = null
)

data class PaymentMethod(
    @SerializedName("_id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("upiId") val upiId: String? = null,
    @SerializedName("card") val card: CardDetails? = null,
    @SerializedName("isDefault") val isDefault: Boolean = false
)

data class CardDetails(
    @SerializedName("cardNumberMasked") val cardNumberMasked: String,
    @SerializedName("cardHolder") val cardHolder: String,
    @SerializedName("expiryMonth") val expiryMonth: String,
    @SerializedName("expiryYear") val expiryYear: String
)

// Form Data class
data class FormData(
    val type: String,
    val upiId: String,
    val card: CardDetails,
    val isDefault: Boolean
)