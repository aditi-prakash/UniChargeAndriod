package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.unichargeandroid.data.model.*

@Composable
fun EnhancedBookingModal(
    station: Station,
    connector: Connector,
    isOpen: Boolean,
    onClose: () -> Unit,
    onConfirm: (BookingData) -> Unit,
    loading: Boolean = false,
    user: User?
) {
    if (isOpen) {
        Dialog(onDismissRequest = onClose) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }
                val vehicles = user?.vehicles ?: emptyList()
                val estimatedCost = remember { calculateEstimatedCost(connector) }

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EvStation,
                            contentDescription = "Booking",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Book Charging Session",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Station & Connector Details
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Charging Details",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            InfoRow(
                                icon = Icons.Default.EvStation,
                                label = "Station",
                                value = station.name
                            )
                            InfoRow(
                                icon = Icons.Default.Business,
                                label = "Brand",
                                value = station.brand
                            )
                            InfoRow(
                                icon = Icons.Default.Power,
                                label = "Connector Type",
                                value = connector.type
                            )
                            InfoRow(
                                icon = Icons.Default.Bolt,
                                label = "Power Output",
                                value = "${connector.power} kW"
                            )
                            InfoRow(
                                icon = Icons.Default.AttachMoney,
                                label = "Price",
                                value = "₹${connector.pricePerKwh}/kWh"
                            )
                            InfoRow(
                                icon = Icons.Default.ElectricalServices,
                                label = "Output",
                                value = connector.output
                            )
                            InfoRow(
                                icon = Icons.Default.Circle,
                                label = "Status",
                                value = connector.status,
//                                valueColor = if (connector.status == "available") Color(0xFF4CAF50) else Color(0xFFFF9800)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Vehicle Selection
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Select Vehicle",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            if (vehicles.isEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "No Vehicles",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "No Vehicles Added",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = "Please add a vehicle to proceed with booking",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            } else {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(vehicles) { vehicle ->
                                        VehicleSelectionCard(
                                            vehicle = vehicle,
                                            isSelected = selectedVehicle?.id == vehicle.id,
                                            onSelected = { selectedVehicle = vehicle }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cost Estimation
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Cost Estimation",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            InfoRow(
                                icon = Icons.Default.BatteryChargingFull,
                                label = "Estimated Energy",
                                value = "32 kWh"
                            )
                            InfoRow(
                                icon = Icons.Default.AttachMoney,
                                label = "Rate",
                                value = "₹${connector.pricePerKwh}/kWh"
                            )
                            InfoRow(
                                icon = Icons.Default.Receipt,
                                label = "Estimated Cost",
                                value = "₹$estimatedCost",
//                                valueColor = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Wallet Balance
                            val walletBalance = user?.wallet?.balance ?: 0.0
                            InfoRow(
                                icon = Icons.Default.AccountBalanceWallet,
                                label = "Wallet Balance",
                                value = "₹$walletBalance",
//                                valueColor = if (walletBalance >= estimatedCost) Color(0xFF4CAF50) else Color(0xFFF44336)
                            )

                            if (walletBalance < estimatedCost) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Insufficient balance. Please add funds to your wallet.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Terms and Conditions
                    Text(
                        text = "By confirming, you agree to:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    val terms = listOf(
                        "Charging session starts automatically in 30 minutes",
                        "Pay for actual energy consumed",
                        "Cancel at least 15 minutes before to avoid charges",
                        "Station availability is subject to change"
                    )

                    terms.forEach { term ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "•",
                                modifier = Modifier.width(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = term,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onClose) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (selectedVehicle != null) {
                                    onConfirm(
                                        BookingData(
                                            vehicleId = selectedVehicle!!.id,
                                            estimatedCost = estimatedCost
                                        )
                                    )
                                }
                            },
                            enabled = selectedVehicle != null && !loading &&
                                    (user?.wallet?.balance ?: 0.0) >= estimatedCost
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Processing...")
                            } else {
                                Icon(Icons.Default.Delete, contentDescription = "Confirm")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Confirm Booking")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleSelectionCard(
    vehicle: Vehicle,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onSelected() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder() else null
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.DirectionsCar,
                contentDescription = "Vehicle",
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${vehicle.make} ${vehicle.model}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${vehicle.batteryCapacityKwh} kWh",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = vehicle.preferredConnector,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

private fun calculateEstimatedCost(connector: Connector): Double {
    val estimatedEnergy = 32.0 // kWh
    return estimatedEnergy * connector.pricePerKwh
}