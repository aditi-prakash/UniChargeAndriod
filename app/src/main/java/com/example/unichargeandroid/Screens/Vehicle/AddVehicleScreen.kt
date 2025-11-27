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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {

    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var batteryCapacityKwh by remember { mutableStateOf("") }
    var preferredConnector by remember { mutableStateOf("") }

    val connectorTypes = listOf("CCS2", "CHAdeMO", "Type2", "GB/T")

    var expandedConnector by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = modifier,
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
                            onClick = onBackClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    colorFilter = ColorFilter.tint(colors.onBackground)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Add Vehicle",
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

            // Make (Brand)
            TextFieldWithLabel(
                label = "Make",
                value = make,
                onValueChange = { make = it },
                placeholder = "Enter vehicle make (e.g., Tesla, BMW)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Model
            TextFieldWithLabel(
                label = "Model",
                value = model,
                onValueChange = { model = it },
                placeholder = "Enter vehicle model (e.g., Model 3, i4)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Battery Capacity
            TextFieldWithLabel(
                label = "Battery Capacity (kWh)",
                value = batteryCapacityKwh,
                onValueChange = { batteryCapacityKwh = it },
                placeholder = "Enter battery capacity in kWh",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preferred Connector - Dropdown
            DropdownField(
                label = "Preferred Connector",
                value = preferredConnector,
                onValueChange = { preferredConnector = it },
                options = connectorTypes,
                expanded = expandedConnector,
                onExpandedChange = { expandedConnector = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Validate and add the vehicle
                    if (validateInputs(make, model, batteryCapacityKwh, preferredConnector)) {
                        // Handle add vehicle logic
                        val vehicleData = mapOf(
                            "make" to make,
                            "model" to model,
                            "batteryCapacityKwh" to batteryCapacityKwh.toDouble(),
                            "preferredConnector" to preferredConnector
                        )
                        // Call your API or save to database
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
                    text = "Add Vehicle",
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
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text
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

// Validation function
private fun validateInputs(
    make: String,
    model: String,
    batteryCapacity: String,
    connector: String
): Boolean {
    return make.isNotBlank() &&
            model.isNotBlank() &&
            batteryCapacity.isNotBlank() &&
            batteryCapacity.toDoubleOrNull() != null &&
            connector.isNotBlank()
}

@Preview(showBackground = true)
@Composable
fun PreviewAddVehicleScreen() {
    MaterialTheme {
        AddVehicleScreen()
    }
}