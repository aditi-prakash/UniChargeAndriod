package com.example.unichargeandroid.Screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

@Composable
fun ActiveChargingSessionScreen(
    onClose: () -> Unit,
    onStopCharging: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // -------------------- FIXED TOP SECTION --------------------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Bolt,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            "Charging in Progress",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground
                        )
                        Text(
                            "Your vehicle is actively charging",
                            fontSize = 14.sp,
                            color = colors.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = colors.onBackground)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Progress Bar
            Text("Charging Progress", fontWeight = FontWeight.Medium, color = colors.onBackground)
            Spacer(Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = colors.primary
            )
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("0%", color = colors.onBackground)
                Text("50%", color = colors.onBackground)
                Text("100%", color = colors.onBackground)
            }

            Spacer(Modifier.height(24.dp))

            // Metrics Row 1
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Time Elapsed", "00:15:23", Icons.Default.AccessTime, Modifier.weight(1f))
                MetricCard("Time Remaining", "00:44:37", Icons.Default.AccessTime, Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            // Metrics Row 2
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Energy Consumed", "12.5 kWh", Icons.Default.BatteryChargingFull, Modifier.weight(1f))
                MetricCard("Current Cost", "₹150.00", Icons.Default.CreditCard, Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(8.dp))

        // -------------------- SCROLLABLE BELOW --------------------
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            // Station Details
            Text("Station Details", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = colors.onBackground)
            Spacer(Modifier.height(8.dp))
            DetailCard("Station Name", "Charge Point — Sector 21")
            DetailCard("Operator", "UniCharge")
            DetailCard("Power Output", "22 kW")
            DetailCard("Connector Type", "Type 2")
            DetailCard("Rate", "₹12/kWh")

            Spacer(Modifier.height(16.dp))

            // Vehicle Details
            Text("Vehicle Details", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = colors.onBackground)
            Spacer(Modifier.height(8.dp))
            DetailCard("Vehicle Model", "Tesla Model 3")
            DetailCard("Battery Capacity", "75 kWh")
            DetailCard("Connector Type", "Type 2")

            Spacer(Modifier.height(24.dp))

            // Actions
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onStopCharging,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Icon(Icons.Default.StopCircle, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Stop Charging", color = Color.White)
                }
                Button(onClick = onClose) {
                    Text("Minimize")
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun MetricCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Card(shape = RoundedCornerShape(12.dp), modifier = modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
            Icon(icon, contentDescription = null, tint = colors.primary)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(label, fontSize = 12.sp, color = colors.onSurface.copy(alpha = 0.6f))
                Text(value, fontWeight = FontWeight.Medium, color = colors.onSurface)
            }
        }
    }
}

@Composable
fun DetailCard(title: String, value: String) {
    val colors = MaterialTheme.colorScheme
    Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Medium, color = colors.onSurface, modifier = Modifier.weight(1f))
            Text(value, fontWeight = FontWeight.SemiBold, color = colors.onSurface)
        }
    }
}


