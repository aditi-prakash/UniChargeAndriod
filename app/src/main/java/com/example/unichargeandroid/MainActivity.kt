package com.example.unichargeandroid

import WalletScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unichargeandroid.Screens.Account.AccountScreen
import com.example.unichargeandroid.Screens.Home.HomeScreen
import com.example.unichargeandroid.Screens.Vehicle.AddVehicleScreen
import com.example.unichargeandroid.Screens.Vehicle.VehicleScreen
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            UniChargeAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HomeScreen,
                        builder = {
                            composable(Routes.HomeScreen){
                                HomeScreen(navController)
                            }

                            composable(Routes.VehicleScreen){
                                VehicleScreen(navController)
                            }

                            composable(Routes.WalletScreen){
                                WalletScreen(navController)
                            }

                            composable(Routes.AccountScreen){
                                AccountScreen(navController)
                            }
                        }
                    )
                }
            }
        }
    }
}


object Routes {
    var HomeScreen = "HomeScreen"
    var VehicleScreen = "VehicleScreen"
    var WalletScreen = "WalletScreen"
    var AccountScreen = "AccountScreen"
}
