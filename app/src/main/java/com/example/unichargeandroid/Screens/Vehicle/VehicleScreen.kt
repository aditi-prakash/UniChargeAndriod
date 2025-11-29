package com.example.unichargeandroid.Screens.Vehicle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.viewmodels.AuthViewModel


@Composable
fun VehicleScreen(
    navController : NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

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