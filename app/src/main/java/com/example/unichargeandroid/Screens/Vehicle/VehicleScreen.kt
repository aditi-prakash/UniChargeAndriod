package com.example.unichargeandroid.Screens.Vehicle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.R
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.BottomNavBar
import com.example.unichargeandroid.Screens.Components.VehicleCard
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.data.model.Vehicle
import com.example.unichargeandroid.viewmodels.AuthViewModel

@Composable
fun VehicleScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Get user's vehicles or empty list if null
    val vehicles = currentUser?.vehicles ?: emptyList()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> {
                navController.navigate(Routes.OnBoardingScreen1) {
                    popUpTo(Routes.AccountScreen) { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.AddVehicleScreen)
                },
                containerColor = colors.primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Vehicle")
            }
        },
        bottomBar = { BottomNavBar(navController, Routes.VehicleScreen) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(top = 15.dp, start = 18.dp, end = 18.dp, bottom = 18.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "My Vehicles",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                style = typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (vehicles.isEmpty()) {
                // Empty state when no vehicles
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vehicle2),
                            contentDescription = "Vehicle Image",
                            modifier = Modifier.size(220.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "No vehicles added",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            style = typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Tap the + button to add your first vehicle",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            style = typography.bodySmall
                        )
                    }
                }
            } else {
                // List of vehicles
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 10.dp)
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCard(
                            vehicleName = "${vehicle.make} ${vehicle.model}",
                            model = "${vehicle.batteryCapacityKwh} kWh Battery",
                            connectorType = vehicle.preferredConnector,
                            isPrimary = isPrimaryVehicle(vehicle, vehicles),
                            onEdit = {
                                // Navigate to edit vehicle screen with vehicle object
                                navController.navigate("${Routes.EditVehicleScreen}/${vehicle.id}")
                            },
                            onDelete = {
                                deleteVehicle(authViewModel, vehicle.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

// Helper function to determine if a vehicle is primary
private fun isPrimaryVehicle(vehicle: Vehicle, vehicles: List<Vehicle>): Boolean {
    return vehicles.indexOf(vehicle) == 0
}

// Function to handle vehicle deletion
private fun deleteVehicle(authViewModel: AuthViewModel, vehicleId: String) {
    val currentUser = authViewModel.currentUser.value
    if (currentUser != null) {
        val updatedVehicles = currentUser.vehicles.filter { it.id != vehicleId }
        val updatedUser = currentUser.copy(vehicles = updatedVehicles)

        // Update user in ViewModel (this would typically call an API)
        // authViewModel.updateUser(updatedUser)

        // For now, we'll just show a snackbar or handle UI state
        // You might want to add a confirmation dialog before deletion
    }
}