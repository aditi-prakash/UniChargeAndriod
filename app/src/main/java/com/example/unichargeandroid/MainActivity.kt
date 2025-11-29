package com.example.unichargeandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unichargeandroid.Screens.Account.AboutScreen
import com.example.unichargeandroid.Screens.Account.AccountScreen
import com.example.unichargeandroid.Screens.Account.EditProfileScreen
import com.example.unichargeandroid.Screens.Account.HistoryScreen
import com.example.unichargeandroid.Screens.Account.LanguageScreen
import com.example.unichargeandroid.Screens.Account.PrivacyPolicyScreen
import com.example.unichargeandroid.Screens.Account.SecurityScreen
import com.example.unichargeandroid.Screens.Account.ThemeScreen
import com.example.unichargeandroid.Screens.Account.HelpCenterScreen
import com.example.unichargeandroid.Screens.Auth.AuthScreen
import com.example.unichargeandroid.screens.Auth.SignInScreen
import com.example.unichargeandroid.Screens.Auth.SignUpScreen
import com.example.unichargeandroid.Screens.Home.HomeScreen
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen1
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen2
import com.example.unichargeandroid.Screens.OnBoarding.OnBoardingScreen3
import com.example.unichargeandroid.Screens.Vehicle.AddVehicleScreen
import com.example.unichargeandroid.Screens.Wallet.WalletScreen
import com.example.unichargeandroid.Screens.Vehicle.VehicleScreen
import com.example.unichargeandroid.Screens.Wallet.PaymentMethodsScreen
import com.example.unichargeandroid.data.local.TokenManager
import com.example.unichargeandroid.data.local.UserManager
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.data.model.User
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme
import com.example.unichargeandroid.viewmodels.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.initialize(this)
        UserManager.initialize(this)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            UniChargeAndroidTheme {
                val bgColor = MaterialTheme.colorScheme.background.toArgb()

                SideEffect {
                    window.navigationBarColor = bgColor
                    val controller = WindowCompat.getInsetsController(window, window.decorView)
                    controller.isAppearanceLightNavigationBars = true
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()

    // Determine start destination based on auth state
    LaunchedEffect(authState) {
        when (authState) {
            AuthState.Authenticated -> {
                if (navController.currentDestination?.route != Routes.HomeScreen) {
                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.AuthScreen) { inclusive = true }
                    }
                }
            }
            AuthState.Unauthenticated -> {
                if (navController.currentDestination?.route != Routes.OnBoardingScreen1 &&
                    navController.currentDestination?.route != Routes.AuthScreen
                ) {
                    navController.navigate(Routes.OnBoardingScreen1) {
                        popUpTo(0)
                    }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = when (authState) {
            AuthState.Authenticated -> Routes.HomeScreen
            else -> Routes.OnBoardingScreen1
        }
    ) {
        // Onboarding Flow
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
                onNextClick = { navController.navigate(Routes.AuthScreen) }
            )
        }

        // Auth Flow
        composable(Routes.AuthScreen) {
            AuthScreen(
                onSignInWithEmail = { navController.navigate(Routes.SignInScreen) },
                onSignUp = { navController.navigate(Routes.SignUpScreen) },
                onGoogleSignIn = {
                    // Handle Google sign in
//                    authViewModel.setAuthenticated("google_token")
                }
            )
        }

        composable(Routes.SignInScreen) {
            SignInScreen(
                onBackClick = { navController.popBackStack() },
                onLoginSuccess = { token: String, user: User ->
                    authViewModel.setAuthenticated(token, user)
                },
                onSignUpClick = { navController.navigate(Routes.SignUpScreen) }
            )
        }

        composable(Routes.SignUpScreen) {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpSuccess = {
                    // After successful signup, navigate to login
                    navController.navigate(Routes.SignInScreen) {
                        popUpTo(Routes.SignUpScreen) { inclusive = true }
                    }
                },
                onSignInClick = { navController.navigate(Routes.SignInScreen) }
            )
        }

        // Main App Flow (Protected Routes)
        composable(Routes.HomeScreen) {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Routes.VehicleScreen) {
            VehicleScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Routes.WalletScreen) {
            WalletScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Routes.AccountScreen) {
            AccountScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Other screens...
        composable(Routes.AddVehicleScreen) {
            AddVehicleScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.PaymentMethodsScreen) {
            PaymentMethodsScreen(navController)
        }

        composable(Routes.AboutScreen) {
            AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.EditProfileScreen) {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HelpCenterScreen) {
            HelpCenterScreen(
                onBackClick = { navController.popBackStack() },
            )
        }

        composable(Routes.HistoryScreen) {
            HistoryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.LanguageScreen) {
            LanguageScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.PrivacyPolicyScreen) {
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() },
            )
        }

        composable(Routes.SecurityScreen) {
            SecurityScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.ThemeScreen) {
            ThemeScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

object Routes {
    const val OnBoardingScreen1 = "OnBoardingScreen1"
    const val OnBoardingScreen2 = "OnBoardingScreen2"
    const val OnBoardingScreen3 = "OnBoardingScreen3"

    const val AuthScreen = "AuthScreen"
    const val SignInScreen = "SignInScreen"
    const val SignUpScreen = "SignUpScreen"

    const val HomeScreen = "HomeScreen"
    const val VehicleScreen = "VehicleScreen"
    const val WalletScreen = "WalletScreen"
    const val AccountScreen = "AccountScreen"

    const val AddVehicleScreen = "AddVehicleScreen"
    const val PaymentMethodsScreen = "PaymentMethodsScreen"

    const val AboutScreen = "AboutScreen"
    const val EditProfileScreen = "EditProfileScreen"
    const val HelpCenterScreen = "HelpCenterScreen"
    const val HistoryScreen = "HistoryScreen"
    const val LanguageScreen = "LanguageScreen"
    const val PrivacyPolicyScreen = "PrivacyPolicyScreen"
    const val SecurityScreen = "SecurityScreen"
    const val ThemeScreen = "ThemeScreen"
}