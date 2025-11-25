package com.example.unichargeandroid.Screens.Vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(modifier: Modifier = Modifier) {

    var selectBrand1 by remember { mutableStateOf("") }
    var selectBrand2 by remember { mutableStateOf("") }
    var trim by remember { mutableStateOf("") }

    val brands = listOf("Audi", "BMW", "Tesla", "Toyota", "Hyundai")
    val trims = listOf("Base", "Sport", "Luxury", "Premium")

    var expandedBrand1 by remember { mutableStateOf(false) }
    var expandedBrand2 by remember { mutableStateOf(false) }
    var expandedTrim by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 40.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Handle back */ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Add Vehicle",
                    fontSize = 28.sp,
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

            DropdownField(
                label = "Select Brand",
                value = selectBrand1,
                onValueChange = { selectBrand1 = it },
                options = brands,
                expanded = expandedBrand1,
                onExpandedChange = { expandedBrand1 = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownField(
                label = "Select Brand",
                value = selectBrand2,
                onValueChange = { selectBrand2 = it },
                options = brands,
                expanded = expandedBrand2,
                onExpandedChange = { expandedBrand2 = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownField(
                label = "Trim",
                value = trim,
                onValueChange = { trim = it },
                options = trims,
                expanded = expandedTrim,
                onExpandedChange = { expandedTrim = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Add the vehicle action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary
                )
            ) {
                Text(
                    text = "Add the Vehicle",
                    fontSize = 16.sp,
                    color = colors.onPrimary
                )
            }
        }
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

    Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = colors.onBackground)
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
                text = "Type $label",
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
                .padding(start = 16.dp, end = 36.dp)
        )

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Dropdown",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .clickable { onExpandedChange(!expanded) },
            tint = colors.onSurface
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddVehicleScreenPreview() {
    AddVehicleScreen()
}
