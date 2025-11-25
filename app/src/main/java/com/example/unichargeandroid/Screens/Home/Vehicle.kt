package com.example.unichargeandroid.Screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R
import com.example.unichargeandroid.Screens.Components.HomeBottomNav
@Composable
fun VehicleScreenAdaptive(
    onAddVehicleClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddVehicleClick,
                containerColor = colors.primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Vehicle")
            }
        },
        bottomBar = { HomeBottomNav() }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {

            Text(
                text = "My Vehicle",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                style = typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp), // fixed height for smaller screens
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vehicle2),
                        contentDescription = "Vehicle Image",
                        modifier = Modifier.size(220.dp) // reduced image size
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No vehicles added",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        style = typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VehicleScreenAdaptivePreview() {
    MaterialTheme {
        VehicleScreenAdaptive()
    }
}
