package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.unichargeandroid.data.model.Route
import com.example.unichargeandroid.data.model.Station

@Composable
fun NavigationModal(
    station: Station,
    userAtStation: Boolean,
    distanceToStation: Double?,
    navigationRoute: Route?,
    onStartMonitoring: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
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
                        imageVector = Icons.Default.Navigation,
                        contentDescription = "Navigation",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Navigate to Station",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Station Information
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Station Details",
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
                            icon = Icons.Default.AttachMoney,
                            label = "Price",
                            value = "₹${station.pricePerKWh ?: "N/A"}/kWh"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Distance Information
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Distance Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (distanceToStation != null) {
                            InfoRow(
                                icon = Icons.Default.Place,
                                label = "Distance to station",
                                value = "${"%.1f".format(distanceToStation)} meters"
                            )
                        }

                        if (navigationRoute != null) {
                            InfoRow(
                                icon = Icons.Default.DirectionsCar,
                                label = "Estimated travel time",
                                value = "${navigationRoute.duration.toInt()} minutes"
                            )
                            InfoRow(
                                icon = Icons.Default.Speed,
                                label = "Distance",
                                value = "${"%.1f".format(navigationRoute.distance)} km"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Location Status
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (userAtStation) Color(0xFF4CAF50) else Color(0xFFFF9800)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (userAtStation) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = "Location Status",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (userAtStation)
                                "You're at the station! Ready to charge."
                            else
                                "Navigate to the station to start charging",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Instructions
                if (!userAtStation) {
                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val instructions = listOf(
                        "Start navigation to reach the charging station",
                        "The app will monitor your location automatically",
                        "Booking will be enabled when you arrive at the station",
                        "Charging can begin once you're within 100 meters"
                    )

                    instructions.forEachIndexed { index, instruction ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${index + 1}.",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = instruction,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onStartMonitoring,
                        enabled = !userAtStation
                    ) {
                        Icon(Icons.Default.Navigation, contentDescription = "Start Navigation")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (userAtStation) "At Station" else "Start Navigation")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}