package com.example.unichargeandroid.Screens.Booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

// -------------------- MODELS --------------------
data class Vehicle(
    val id: String,
    val make: String,
    val model: String,
    val batteryCapacityKwh: Double,
    val preferredConnector: String
)

data class Connector(
    val id: String,
    val type: String,
    val power: Double,
    val output: String,
    val status: String
)

data class Station(
    val id: String,
    val station: String,
    val brand: String,
    val powerKW: Double,
    val pricePerKWh: Double
)

data class User(
    val vehicles: List<Vehicle>,
    val walletBalance: Double
)

// -------------------- VEHICLE BOOKING SCREEN --------------------
@Composable
fun BookingScreen(
    station: Station,
    connector: Connector,
    user: User,
    onBackClick: () -> Unit = {},
    onVehicleSelected: (Vehicle) -> Unit = {},
    onConfirm: () -> Unit = {},
    onAddBalance: () -> Unit = {}
) {
    var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // -------------------- TOP BAR --------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Booking",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Select vehicle and confirm",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // -------------------- SCROLLABLE CONTENT --------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            // -------------------- STATION INFO --------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Station Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${station.station} (${station.brand})")
                    Text("Power: ${station.powerKW} kW")
                    Text("Price: ₹${station.pricePerKWh}/kWh")
                    Text("Connector: ${connector.type} • ${connector.power}kW • ${connector.output}")
                    Text(
                        "Status: ${connector.status}",
                        color = if (connector.status == "available") Color.Green else Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------- VEHICLE SELECTION --------------------
            Text(
                "Select Your Vehicle (Optional)",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface // <-- ensure proper color in dark/light theme
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(user.vehicles) { vehicle ->
                    val isSelected = selectedVehicle?.id == vehicle.id
                    Card(
                        modifier = Modifier
                            .width(150.dp)
                            .clickable {
                                selectedVehicle = vehicle
                                onVehicleSelected(vehicle)
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "${vehicle.make} ${vehicle.model}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Battery: ${vehicle.batteryCapacityKwh} kWh", fontSize = 12.sp)
                            Text("Connector: ${vehicle.preferredConnector}", fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------- BOOKING INFO --------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Booking Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("⏰ Session starts automatically in 30 minutes")
                    Text("⚡ Charging until 80% battery")
                    Text("💰 Pay for actual energy consumed")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------- PAYMENT INFO --------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Payment Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Estimated Energy: 32 kWh")
                    Text("Rate: ₹${station.pricePerKWh}/kWh")
                    Text("Estimated Cost: ₹384")
                    Text("Wallet Balance: ₹${user.walletBalance}", color = Color.Green, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Insufficient balance? Add funds.",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onAddBalance() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------- ACTION BUTTONS --------------------
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* Cancel */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { onConfirm() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirm Booking")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// -------------------- PREVIEW --------------------
@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewVehicleBookingScreenDark() {
    val user = User(
        vehicles = listOf(
            Vehicle("1", "Tesla", "Model 3", 75.0, "CCS2"),
            Vehicle("2", "Tata", "Nexon EV", 40.0, "Type2"),
            Vehicle("3", "MG", "ZSE", 44.5, "Type2")
        ),
        walletBalance = 500.0
    )
    val station = Station("st1", "UniCharge Station", "UniCharge", 50.0, 12.0)
    val connector = Connector("c1", "CCS2", 50.0, "DC", "available")

    UniChargeAndroidTheme(darkTheme = true) {
        BookingScreen(
            station = station,
            connector = connector,
            user = user,
            onBackClick = {},
            onVehicleSelected = {},
            onConfirm = {},
            onAddBalance = {}
        )
    }
}
