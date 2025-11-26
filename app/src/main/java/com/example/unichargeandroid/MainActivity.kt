package com.example.unichargeandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unichargeandroid.Screens.Auth.LoginScreen
import com.example.unichargeandroid.Screens.Account.AboutScreen
import com.example.unichargeandroid.Screens.Account.AccountScreen
import com.example.unichargeandroid.Screens.Account.EditProfileScreen
import com.example.unichargeandroid.Screens.Account.HistoryScreen
import com.example.unichargeandroid.Screens.Account.LanguageScreen
import com.example.unichargeandroid.Screens.Account.PrivacyPolicyScreen
import com.example.unichargeandroid.Screens.Account.SecurityScreen
import com.example.unichargeandroid.Screens.Account.ThemeScreen
import com.example.unichargeandroid.Screens.Account.HelpCenterScreen
import com.example.unichargeandroid.screens.Auth.SignInScreen
import com.example.unichargeandroid.Screens.Auth.SignUpScreen
import com.example.unichargeandroid.Screens.Home.HomeScreen
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen1
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen2
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen3
import com.example.unichargeandroid.Screens.Vehicle.AddVehicleScreen
import com.example.unichargeandroid.Screens.Wallet.WalletScreen
import com.example.unichargeandroid.Screens.Vehicle.VehicleScreen
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            UniChargeAndroidTheme {
                val bgColor =
                    MaterialTheme.colorScheme.background.toArgb() // ✔ read inside composable block

                SideEffect {
                    window.navigationBarColor = bgColor

                    val controller = WindowCompat.getInsetsController(window, window.decorView)
                    controller.isAppearanceLightNavigationBars = true
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.OnBoardingScreen1,
                        builder = {
                            composable(Routes.OnBoardingScreen1) {
                                OnBoardingScreen1(
                                    onBackClick = { navController.popBackStack() },
                                    onNextClick = { navController.navigate(Routes.OnBoardingScreen2) }
                                )
                            }
                            composable(Routes.OnBoardingScreen2) {
                                OnBoardingScreen2(
                                    onBackClick = { navController.popBackStack() },
                                    onNextClick = { navController.navigate(Routes.OnBoardingScreen3) }
                                )
                            }
                            composable(Routes.OnBoardingScreen3) {
                                OnBoardingScreen3(
                                    onBackClick = { navController.popBackStack() },
                                    onNextClick = { navController.navigate(Routes.LoginScreen) }
                                )
                            }
                            composable(Routes.LoginScreen) {
                                LoginScreen(
                                    onSignInWithEmail = { navController.navigate(Routes.SignInScreen) },
                                    onSignUp = { navController.navigate(Routes.SignUpScreen) },
                                    onGoogleSignIn = { navController.navigate(Routes.HomeScreen) }
                                )
                            }
                            composable(Routes.SignInScreen) {
                                SignInScreen()
                            }
                            composable(Routes.SignUpScreen) {
                                SignUpScreen()
                            }

                            composable(Routes.HomeScreen) {
                                HomeScreen(navController)
                            }

                            composable(Routes.VehicleScreen) {
                                VehicleScreen(navController)
                            }

                            composable(Routes.WalletScreen) {
                                WalletScreen(navController)
                            }

                            composable(Routes.AccountScreen) {
                                AccountScreen(navController)
                            }
                            composable(Routes.AddVehicleScreen) {
                                AddVehicleScreen()
                            }

                            composable(Routes.AboutScreen) {
                                AboutScreen()
                            }
                            composable(Routes.EditProfileScreen) {
                                EditProfileScreen()
                            }
                            composable(Routes.HelpCenterScreen) {
                                HelpCenterScreen()
                            }
                            composable(Routes.HistoryScreen) {
                                HistoryScreen()
                            }
                            composable(Routes.LanguageScreen) {
                                LanguageScreen()
                            }
                            composable(Routes.PrivacyPolicyScreen) {
                                PrivacyPolicyScreen()
                            }
                            composable(Routes.SecurityScreen) {
                                SecurityScreen()
                            }
                            composable(Routes.ThemeScreen) {
                                ThemeScreen()
                            }
                        }
                    )
                }
            }
        }
    }
}


object Routes {
    var OnBoardingScreen1 = "OnBoardingScreen1"
    var OnBoardingScreen2 = "OnBoardingScreen2"
    var OnBoardingScreen3 = "OnBoardingScreen3"

    var LoginScreen = "LoginScreen"
    var SignInScreen = "SignInScreen"
    var SignUpScreen = "SignUpScreen"

    var HomeScreen = "HomeScreen"
    var VehicleScreen = "VehicleScreen"
    var WalletScreen = "WalletScreen"
    var AccountScreen = "AccountScreen"

    var AddVehicleScreen = "AddVehicleScreen"

    var AboutScreen = "AboutScreen"
    var EditProfileScreen = "EditProfileScreen"
    var HelpCenterScreen = "HelpCenterScreen"
    var HistoryScreen = "HistoryScreen"
    var LanguageScreen = "LanguageScreen"
    var PrivacyPolicyScreen = "PrivacyPolicyScreen"
    var SecurityScreen = "SecurityScreen"
    var ThemeScreen = "ThemeScreen"
}
