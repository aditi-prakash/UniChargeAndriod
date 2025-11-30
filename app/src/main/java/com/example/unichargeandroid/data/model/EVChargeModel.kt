package com.example.unichargeandroid.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val name: String
)

data class Station(
    @SerializedName("_id") val id: String,
    @SerializedName("station") val name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("powerKW") val powerKW: Double?,
    @SerializedName("pricePerKWh") val pricePerKWh: Double?,
    @SerializedName("PortNum") val portNum: Int?
)

data class Connector(
    val id: String,
    val type: String,
    val power: Double,
    val status: String,
    val pricePerKwh: Double,
    val output: String
)

data class Route(
    val coordinates: List<Coordinate>,
    val distance: Double, // km
    val duration: Double // minutes
)

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

data class BookingRequest(
    val stationId: String,
    val connectorId: String,
    val vehicleId: String,
    val scheduledStart: Date,
    val scheduledEnd: Date,
    val estimatedDuration: Int,
    val estimatedCost: Double
)

data class CompleteBookingRequest(
    val finalBatteryLevel: Double,
    val energyConsumed: Double,
    val totalCost: Double
)

data class BookingData(
    val vehicleId: String,
    val estimatedCost: Double
)

// Extend your existing Booking model if needed
data class Booking(
    @SerializedName("_id") val id: String,
    @SerializedName("stationId") val stationId: String,
    @SerializedName("connectorId") val connectorId: String,
    @SerializedName("vehicleId") val vehicleId: String,
    @SerializedName("status") val status: String,
    @SerializedName("scheduledStart") val scheduledStart: Date,
    @SerializedName("scheduledEnd") val scheduledEnd: Date,
    @SerializedName("estimatedDuration") val estimatedDuration: Int,
    @SerializedName("estimatedCost") val estimatedCost: Double,
    @SerializedName("actualStart") val actualStart: Date? = null,
    @SerializedName("actualEnd") val actualEnd: Date? = null
)