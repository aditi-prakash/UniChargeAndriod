package com.example.unichargeandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unichargeandroid.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.*

class EVChargeHubViewModel : ViewModel() {

    // States matching React component
    private val _userLocation = MutableStateFlow<LocationData?>(null)
    val userLocation: StateFlow<LocationData?> = _userLocation.asStateFlow()

    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    val stations: StateFlow<List<Station>> = _stations.asStateFlow()

    private val _filteredStations = MutableStateFlow<List<Station>>(emptyList())
    val filteredStations: StateFlow<List<Station>> = _filteredStations.asStateFlow()

    private val _selectedStation = MutableStateFlow<Station?>(null)
    val selectedStation: StateFlow<Station?> = _selectedStation.asStateFlow()

    private val _selectedConnector = MutableStateFlow<Connector?>(null)
    val selectedConnector: StateFlow<Connector?> = _selectedConnector.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchRadius = MutableStateFlow("")
    val searchRadius: StateFlow<String> = _searchRadius.asStateFlow()

    private val _showStations = MutableStateFlow(false)
    val showStations: StateFlow<Boolean> = _showStations.asStateFlow()

    private val _navigationRoute = MutableStateFlow<Route?>(null)
    val navigationRoute: StateFlow<Route?> = _navigationRoute.asStateFlow()

    private val _userAtStation = MutableStateFlow(false)
    val userAtStation: StateFlow<Boolean> = _userAtStation.asStateFlow()

    private val _distanceToStation = MutableStateFlow<Double?>(null)
    val distanceToStation: StateFlow<Double?> = _distanceToStation.asStateFlow()

    private val _isBookingModalOpen = MutableStateFlow(false)
    val isBookingModalOpen: StateFlow<Boolean> = _isBookingModalOpen.asStateFlow()

    private val _isNavigationModalOpen = MutableStateFlow(false)
    val isNavigationModalOpen: StateFlow<Boolean> = _isNavigationModalOpen.asStateFlow()

    private val _confirmedBooking = MutableStateFlow<Booking?>(null)
    val confirmedBooking: StateFlow<Booking?> = _confirmedBooking.asStateFlow()

    private val _activeChargingSession = MutableStateFlow<Booking?>(null)
    val activeChargingSession: StateFlow<Booking?> = _activeChargingSession.asStateFlow()

    private val _showActiveSession = MutableStateFlow(false)
    val showActiveSession: StateFlow<Boolean> = _showActiveSession.asStateFlow()

    private val _showTopBar = MutableStateFlow(false)
    val showTopBar: StateFlow<Boolean> = _showTopBar.asStateFlow()

    private val _currentChargingCost = MutableStateFlow(0.0)
    val currentChargingCost: StateFlow<Double> = _currentChargingCost.asStateFlow()

    // Constants
    companion object {
        private const val LOCATION_THRESHOLD = 100.0 // meters
        private const val DEFAULT_LAT = 20.2961
        private const val DEFAULT_LNG = 85.8245
    }

    // Initialize and fetch user location
    init {
        getUserLocation()
        loadStations()
        // loadUserBookings() - Uncomment when you have the API service
    }

    private fun getUserLocation() {
        _isLoading.value = true
        // TODO: Implement Android location services
        // For now, use default location
        _userLocation.value = LocationData(DEFAULT_LAT, DEFAULT_LNG, "Bhubaneswar")
        _isLoading.value = false
    }

    private fun loadStations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: Replace with actual API call
                // For now, use mock data
                val mockStations = listOf(
                    Station(
                        id = "1",
                        name = "UniCharge Downtown",
                        brand = "UniCharge",
                        latitude = 20.2961,
                        longitude = 85.8245,
                        powerKW = 50.0,
                        pricePerKWh = 12.0,
                        portNum = 4
                    ),
                    Station(
                        id = "2",
                        name = "EV Power Mall",
                        brand = "EV Power",
                        latitude = 20.2900,
                        longitude = 85.8300,
                        powerKW = 22.0,
                        pricePerKWh = 10.5,
                        portNum = 2
                    )
                )
                _stations.value = mockStations
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchStationsNearby() {
        val userLoc = _userLocation.value ?: return

        _isLoading.value = true
        _showStations.value = true

        val nearbyStations = _stations.value.filter { station ->
            calculateDistance(
                userLoc.latitude, userLoc.longitude,
                station.latitude!!, station.longitude!!
            ) <= 5.0 // 5km radius
        }.sortedBy { station ->
            calculateDistance(
                userLoc.latitude, userLoc.longitude,
                station.latitude!!, station.longitude!!
            )
        }

        if (nearbyStations.isNotEmpty()) {
            _filteredStations.value = nearbyStations
            _searchRadius.value = "5km"
        } else {
            // Fallback to 10km
            val stations10km = _stations.value.filter { station ->
                calculateDistance(
                    userLoc.latitude, userLoc.longitude,
                    station.latitude!!, station.longitude!!
                ) <= 10.0
            }.sortedBy { station ->
                calculateDistance(
                    userLoc.latitude, userLoc.longitude,
                    station.latitude!!, station.longitude!!
                )
            }
            _filteredStations.value = stations10km
            _searchRadius.value = "10km"
        }

        _isLoading.value = false
    }

    fun onStationSelected(station: Station) {
        _selectedStation.value = station

        // Generate connectors for the station
        val connectors = generateConnectors(station)
        _selectedConnector.value = connectors.firstOrNull { it.status == "available" } ?: connectors.first()

        // Check if user is at station
        checkUserAtStation(station)

        if (_userAtStation.value) {
            _isBookingModalOpen.value = true
        } else {
            generateNavigationRoute(station)
            _isNavigationModalOpen.value = true
        }
    }

    private fun checkUserAtStation(station: Station) {
        val userLoc = _userLocation.value ?: return

        val distance = calculateDistance(
            userLoc.latitude, userLoc.longitude,
            station.latitude!!, station.longitude!!
        ) * 1000 // Convert to meters

        _distanceToStation.value = distance
        _userAtStation.value = distance <= LOCATION_THRESHOLD
    }

    private fun generateConnectors(station: Station): List<Connector> {
        val portNum = station.portNum ?: 1
        val connectorTypes = listOf("Type2", "CCS", "CHAdeMO")

        return List(portNum) { index ->
            Connector(
                id = "connector_${index + 1}",
                type = connectorTypes[index % connectorTypes.size],
                power = station.powerKW ?: 50.0,
                status = if (Math.random() > 0.3) "available" else "charging",
                pricePerKwh = station.pricePerKWh ?: 10.0,
                output = if (index % 2 == 0) "AC" else "DC"
            )
        }
    }

    private fun generateNavigationRoute(station: Station) {
        val userLoc = _userLocation.value ?: return

        // TODO: Implement actual route calculation using Google Maps API
        val route = Route(
            coordinates = listOf(
                Coordinate(userLoc.latitude, userLoc.longitude),
                Coordinate(station.latitude!!, station.longitude!!)
            ),
            distance = calculateDistance(
                userLoc.latitude, userLoc.longitude,
                station.latitude!!, station.longitude!!
            ),
            duration = 15.0 // Estimated minutes
        )

        _navigationRoute.value = route
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Earth's radius in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    fun confirmBooking(bookingData: BookingData) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: Implement actual API call
                // For now, create mock booking
                val mockBooking = Booking(
                    id = UUID.randomUUID().toString(),
                    stationId = _selectedStation.value?.id ?: "",
                    connectorId = _selectedConnector.value?.id ?: "",
                    vehicleId = bookingData.vehicleId,
                    status = "confirmed",
                    scheduledStart = Date(System.currentTimeMillis() + 30000),
                    scheduledEnd = Date(System.currentTimeMillis() + 3900000),
                    estimatedDuration = 60,
                    estimatedCost = bookingData.estimatedCost
                )

                _confirmedBooking.value = mockBooking
                _isBookingModalOpen.value = false

                // Start charging immediately
                startCharging(mockBooking.id)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun startCharging(bookingId: String) {
        viewModelScope.launch {
            try {
                // TODO: Implement actual API call
                // For now, just update local state
                _activeChargingSession.value = _confirmedBooking.value
                _showActiveSession.value = true
                _showTopBar.value = true
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun stopCharging() {
        val session = _activeChargingSession.value ?: return

        viewModelScope.launch {
            try {
                // TODO: Implement actual API call
                _activeChargingSession.value = null
                _showActiveSession.value = false
                _showTopBar.value = false
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun closeBookingModal() {
        _isBookingModalOpen.value = false
        _selectedStation.value = null
        _selectedConnector.value = null
    }

    fun closeNavigationModal() {
        _isNavigationModalOpen.value = false
        _selectedStation.value = null
    }

    fun closeConfirmation() {
        _confirmedBooking.value = null
        _selectedStation.value = null
        _selectedConnector.value = null
    }

    fun closeActiveSession() {
        _showActiveSession.value = false
    }

    fun updateChargingCost(cost: Double) {
        _currentChargingCost.value = cost
    }

    fun setShowActiveSession(show: Boolean) {
        _showActiveSession.value = show
    }

    fun setShowTopBar(show: Boolean) {
        _showTopBar.value = show
    }

    fun setBookingModalOpen(open: Boolean) {
        _isBookingModalOpen.value = open
    }

    fun setNavigationModalOpen(open: Boolean) {
        _isNavigationModalOpen.value = open
    }

    fun setShowStations(show: Boolean) {
        _showStations.value = show
    }
}