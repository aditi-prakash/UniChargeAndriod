package com.example.unichargeandroid.Screens.Vehicle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.data.model.Vehicle
import com.example.unichargeandroid.viewmodels.AuthViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    editingVehicle: Vehicle? = null
) {

    var make by remember { mutableStateOf(editingVehicle?.make ?: "") }
    var model by remember { mutableStateOf(editingVehicle?.model ?: "") }
    var batteryCapacityKwh by remember {
        mutableStateOf(editingVehicle?.batteryCapacityKwh?.toString() ?: "")
    }
    var preferredConnector by remember {
        mutableStateOf(editingVehicle?.preferredConnector ?: "")
    }

    val connectorTypes = listOf("CCS2", "CHAdeMO", "Type 2", "GB/T", "Type 1", "CCS1")

    var expandedConnector by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val colors = MaterialTheme.colorScheme
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 50.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(
                            onClick = { navController.popBackStack() },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    colorFilter = ColorFilter.tint(colors.onBackground)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = if (editingVehicle != null) "Edit Vehicle" else "Add Vehicle",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colors.background)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Error message
            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Make (Brand)
            TextFieldWithLabel(
                label = "Make",
                value = make,
                onValueChange = {
                    make = it
                    showError = false
                },
                placeholder = "Enter vehicle make (e.g., Tesla, Tata)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Model
            TextFieldWithLabel(
                label = "Model",
                value = model,
                onValueChange = {
                    model = it
                    showError = false
                },
                placeholder = "Enter vehicle model (e.g., Nexon EV)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Battery Capacity
            TextFieldWithLabel(
                label = "Battery Capacity (kWh)",
                value = batteryCapacityKwh,
                onValueChange = {
                    if (it.isEmpty() || it.toDoubleOrNull() != null) {
                        batteryCapacityKwh = it
                        showError = false
                    }
                },
                placeholder = "Enter battery capacity in kWh",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preferred Connector - Dropdown
            DropdownField(
                label = "Preferred Connector",
                value = preferredConnector,
                onValueChange = {
                    preferredConnector = it
                    showError = false
                },
                options = connectorTypes,
                expanded = expandedConnector,
                onExpandedChange = { expandedConnector = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (validateInputs(make, model, batteryCapacityKwh, preferredConnector)) {
                        // Handle add/edit vehicle logic
                        val newVehicle = Vehicle(
                            id = editingVehicle?.id ?: generateVehicleId(currentUser?.vehicles ?: emptyList()),
                            make = make.trim(),
                            model = model.trim(),
                            batteryCapacityKwh = batteryCapacityKwh.toInt(),
                            preferredConnector = preferredConnector,
                            createdAt = editingVehicle?.createdAt ?: getCurrentDateTime()
                        )

                        // Update user's vehicles
                        updateUserVehicles(authViewModel, newVehicle, editingVehicle != null)
                        navController.popBackStack()
                    } else {
                        showError = true
                        errorMessage = "Please fill all fields correctly. Battery capacity must be a number."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary
                ),
                enabled = validateInputs(make, model, batteryCapacityKwh, preferredConnector)
            ) {
                Text(
                    text = if (editingVehicle != null) "Update Vehicle" else "Add Vehicle",
                    fontSize = 16.sp,
                    color = colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun TextFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val colors = MaterialTheme.colorScheme

    Text(
        text = label,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = colors.onBackground
    )
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colors.surface, RoundedCornerShape(28.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = colors.onSurface.copy(alpha = 0.5f),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = colors.onSurface, fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            cursorBrush = SolidColor(colors.primary)
        )
    }
}

@Composable
fun DropdownField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Text(
        text = label,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = colors.onBackground
    )
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colors.surface, RoundedCornerShape(28.dp))
            .clickable { onExpandedChange(!expanded) },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = if (value.isEmpty()) "Select $label" else value,
            color = if (value.isEmpty()) colors.onSurface.copy(alpha = 0.5f) else colors.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Dropdown",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            tint = colors.onSurface
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        onValueChange(item)
                        onExpandedChange(false)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Helper functions
private fun validateInputs(
    make: String,
    model: String,
    batteryCapacity: String,
    connector: String
): Boolean {
    return make.isNotBlank() &&
            model.isNotBlank() &&
            batteryCapacity.isNotBlank() &&
            batteryCapacity.toIntOrNull() != null &&
            batteryCapacity.toInt() > 0 &&
            connector.isNotBlank()
}

private fun generateVehicleId(existingVehicles: List<Vehicle>): String {
    val maxId = existingVehicles.maxByOrNull { it.id.toIntOrNull() ?: 0 }?.id?.toIntOrNull() ?: 0
    return (maxId + 1).toString()
}

private fun getCurrentDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
}

private fun updateUserVehicles(
    authViewModel: AuthViewModel,
    newVehicle: Vehicle,
    isEditing: Boolean
) {
    val currentUser = authViewModel.currentUser.value
    if (currentUser != null) {
        val updatedVehicles = if (isEditing) {
            // Update existing vehicle
            currentUser.vehicles.map { vehicle ->
                if (vehicle.id == newVehicle.id) newVehicle else vehicle
            }
        } else {
            // Add new vehicle
            currentUser.vehicles + newVehicle
        }

        val updatedUser = currentUser.copy(vehicles = updatedVehicles)

        // Update user in ViewModel (this would typically call an API)
        // authViewModel.updateUser(updatedUser)

        // For now, we'll update the local state
        // You can add a snackbar or toast to confirm the action
    }
}