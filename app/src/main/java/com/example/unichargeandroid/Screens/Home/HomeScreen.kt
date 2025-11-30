package com.example.unichargeandroid.Screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.ActiveChargingSession
import com.example.unichargeandroid.Screens.Components.BookingConfirmation
import com.example.unichargeandroid.Screens.Components.BottomNavBar
import com.example.unichargeandroid.Screens.Components.EnhancedBookingModal
import com.example.unichargeandroid.Screens.Components.NavigationModal
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.viewmodel.EVChargeHubViewModel
import com.example.unichargeandroid.viewmodels.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    evChargeViewModel: EVChargeHubViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    val colors = MaterialTheme.colorScheme
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    // EV Charge Hub States
    val userLocation by evChargeViewModel.userLocation.collectAsState()
    val filteredStations by evChargeViewModel.filteredStations.collectAsState()
    val isLoading by evChargeViewModel.isLoading.collectAsState()
    val showStations by evChargeViewModel.showStations.collectAsState()
    val searchRadius by evChargeViewModel.searchRadius.collectAsState()
    val selectedStation by evChargeViewModel.selectedStation.collectAsState()
    val selectedConnector by evChargeViewModel.selectedConnector.collectAsState()
    val isBookingModalOpen by evChargeViewModel.isBookingModalOpen.collectAsState()
    val isNavigationModalOpen by evChargeViewModel.isNavigationModalOpen.collectAsState()
    val confirmedBooking by evChargeViewModel.confirmedBooking.collectAsState()
    val activeChargingSession by evChargeViewModel.activeChargingSession.collectAsState()
    val showActiveSession by evChargeViewModel.showActiveSession.collectAsState()
    val showTopBar by evChargeViewModel.showTopBar.collectAsState()
    val userAtStation by evChargeViewModel.userAtStation.collectAsState()
    val distanceToStation by evChargeViewModel.distanceToStation.collectAsState()
    val navigationRoute by evChargeViewModel.navigationRoute.collectAsState()

    SideEffect {
        systemUiController.isStatusBarVisible = true
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                // User is authenticated, proceed
            }
            is AuthState.Unauthenticated -> {
                navController.navigate(Routes.OnBoardingScreen1) {
                    popUpTo(Routes.HomeScreen) { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            if (showTopBar && activeChargingSession != null) {
                ActiveSessionTopBar(
                    session = activeChargingSession!!,
                    onViewDetails = { evChargeViewModel.setShowActiveSession(true) }
                )
            }
        },
        bottomBar = { BottomNavBar(navController, Routes.HomeScreen) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Main content with fixed search and scrollable content below
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Fixed Header Section (Non-scrollable)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .zIndex(1f) // Ensure it stays above scrollable content
                ) {
                    // Top Row: Title + Notifications
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 15.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "UniCharge",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground
                        )

                        IconButton(
                            onClick = { navController.navigate(Routes.NotificationScreen) },
                            modifier = Modifier
                                .size(48.dp)
                                .background(colors.surface, RoundedCornerShape(16.dp)),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = colors.primary
                            )
                        }
                    }

                    // Search bar with EVChargeHub functionality
                    EnhancedSearchBarSection(
                        userLocation = userLocation,
                        isLoading = isLoading,
                        onSearchClicked = { evChargeViewModel.searchStationsNearby() },
                        onUseMyLocation = { /* TODO: Implement location fetch */ }
                    )
                }

                // Scrollable Content Section
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    // Map Section
                    item {
                        EVChargeMapSection(
                            userLocation = userLocation,
                            stations = filteredStations,
                            selectedStation = selectedStation,
                            navigationRoute = navigationRoute,
                            isNavigationModalOpen = isNavigationModalOpen,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    // Content based on state
                    if (showStations) {
                        if (filteredStations.isNotEmpty()) {
                            // Stations List Header
                            item {
                                StationsListHeader(
                                    stationsCount = filteredStations.size,
                                    searchRadius = searchRadius
                                )
                            }
                            // Stations List
                            items(filteredStations) { station ->
                                StationCard(
                                    station = station,
                                    onBookClick = { evChargeViewModel.onStationSelected(station) },
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        } else {
                            // No Stations Found
                            item {
                                NoStationsFoundSection(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp)
                                )
                            }
                        }
                    } else {
                        // Default Home Content
                        item {
                            DefaultHomeContent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                onFindStations = { evChargeViewModel.searchStationsNearby() }
                            )
                        }
                    }
                }
            }

            // Modals (Overlay - Fixed position)
            if (isNavigationModalOpen && selectedStation != null) {
                NavigationModal(
                    station = selectedStation!!,
                    userAtStation = userAtStation,
                    distanceToStation = distanceToStation,
                    navigationRoute = navigationRoute,
                    onStartMonitoring = { /* TODO: Implement location monitoring */ },
                    onCancel = { evChargeViewModel.setNavigationModalOpen(false) }
                )
            }

            if (isBookingModalOpen && selectedStation != null && selectedConnector != null) {
                EnhancedBookingModal(
                    station = selectedStation!!,
                    connector = selectedConnector!!,
                    isOpen = isBookingModalOpen,
                    onClose = { evChargeViewModel.setBookingModalOpen(false) },
                    onConfirm = evChargeViewModel::confirmBooking,
                    loading = isLoading,
                    user = authViewModel.currentUser.value
                )
            }

            if (confirmedBooking != null && selectedStation != null && selectedConnector != null) {
                BookingConfirmation(
                    booking = confirmedBooking!!,
                    station = selectedStation!!,
                    connector = selectedConnector!!,
                    onClose = evChargeViewModel::closeConfirmation
                )
            }

            if (showActiveSession && activeChargingSession != null) {
                ActiveChargingSession(
                    activeBooking = activeChargingSession!!,
                    onClose = { evChargeViewModel.setShowActiveSession(false) },
                    onStopCharging = evChargeViewModel::stopCharging,
                    onCostUpdate = evChargeViewModel::updateChargingCost
                )
            }
        }
    }
}

@Composable
fun ActiveSessionTopBar(
    session: com.example.unichargeandroid.data.model.Booking,
    onViewDetails: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⚡ Active Charging Session — Station #${session.stationId.take(8)}...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            TextButton(onClick = onViewDetails) {
                Text("View Details")
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View Details",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun EnhancedSearchBarSection(
    userLocation: com.example.unichargeandroid.data.model.LocationData?,
    isLoading: Boolean,
    onSearchClicked: () -> Unit,
    onUseMyLocation: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Location Input Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Search Field
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            color = colors.surfaceVariant,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable { onSearchClicked() }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            userLocation?.name ?: "Search for charging stations...",
                            color = if (userLocation != null) colors.onSurface else Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Use My Location Button
                Button(
                    onClick = onUseMyLocation,
                    enabled = !isLoading,
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.surface,
                        contentColor = colors.primary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            Icons.Default.MyLocation,
                            contentDescription = "Use My Location"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    text = "Find Stations",
                    icon = Icons.Default.EvStation,
                    onClick = onSearchClicked,
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f)
                )

                QuickActionButton(
                    text = "Nearby",
                    icon = Icons.Default.LocationOn,
                    onClick = onSearchClicked,
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
        } else {
            Icon(icon, contentDescription = text, modifier = Modifier.size(16.dp))
        }
        Spacer(Modifier.width(4.dp))
        Text(text, fontSize = 12.sp)
    }
}

@Composable
fun EVChargeMapSection(
    userLocation: com.example.unichargeandroid.data.model.LocationData?,
    stations: List<com.example.unichargeandroid.data.model.Station>,
    selectedStation: com.example.unichargeandroid.data.model.Station?,
    navigationRoute: com.example.unichargeandroid.data.model.Route?,
    isNavigationModalOpen: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Map placeholder content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Map",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Interactive Map",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Stations will appear here",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            // Overlay showing station count if available
            if (stations.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${stations.size} stations",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Show navigation route indicator if available
            if (navigationRoute != null && isNavigationModalOpen) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Navigation Active",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun StationsListHeader(
    stationsCount: Int,
    searchRadius: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Available Stations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Found $stationsCount stations nearby",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Search Radius Badge
            if (searchRadius.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = when (searchRadius) {
                            "5km" -> "Within 5km"
                            "10km" -> "Within 10km"
                            "route" -> "Along route"
                            else -> searchRadius
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun StationCard(
    station: com.example.unichargeandroid.data.model.Station,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Station Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.EvStation,
                        contentDescription = "Station",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = station.brand,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "4.5",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Station Details
            StationDetailRow(
                icon = Icons.Default.Place,
                label = "Station",
                value = station.name
            )
            StationDetailRow(
                icon = Icons.Default.Bolt,
                label = "Power",
                value = "${station.powerKW ?: "N/A"} kW"
            )
            StationDetailRow(
                icon = Icons.Default.AttachMoney,
                label = "Price",
                value = "₹${station.pricePerKWh ?: "N/A"}/kWh"
            )
            StationDetailRow(
                icon = Icons.Default.ElectricalServices,
                label = "Ports",
                value = "${station.portNum ?: "N/A"} available"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Book Button
            Button(
                onClick = onBookClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = "Book")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Book Slot")
            }
        }
    }
}

@Composable
fun StationDetailRow(
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
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun NoStationsFoundSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocationOff,
            contentDescription = "No stations",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Stations Found",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We couldn't find any charging stations in your area. Try expanding your search radius or checking a different location.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun DefaultHomeContent(
    modifier: Modifier = Modifier,
    onFindStations: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.EvStation,
            contentDescription = "EV Charging",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Find EV Charging Stations",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Discover nearby charging stations and book your slot in advance. Get real-time availability and pricing.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onFindStations,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.Search, contentDescription = "Find Stations")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Find Stations Nearby")
        }
    }
}