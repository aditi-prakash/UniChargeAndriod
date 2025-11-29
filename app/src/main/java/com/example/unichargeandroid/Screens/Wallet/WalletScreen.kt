package com.example.unichargeandroid.Screens.Wallet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.BottomNavBar
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.data.model.PaymentMethod
import com.example.unichargeandroid.data.model.User
import com.example.unichargeandroid.data.model.Wallet
import com.example.unichargeandroid.viewmodels.AuthViewModel

@SuppressLint("DefaultLocale")
@Composable
fun WalletScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var showModal by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("") }
    var showOtherOption by remember { mutableStateOf(false) }
    var otherPaymentType by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val currentUser by authViewModel.currentUser.collectAsState()

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

    // Initialize selected method with user's default payment method
    LaunchedEffect(currentUser) {
        currentUser?.wallet?.defaultPaymentMethod?.let { defaultId ->
            selectedMethod = defaultId
        }
    }

    val scrollState = rememberScrollState()
    val minBalance = 500.0
    val isLowBalance = (currentUser?.wallet?.balance ?: 0.0) < minBalance

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar(navController, Routes.WalletScreen) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Header with Add Balance Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Wallet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(
                    onClick = { showModal = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Balance", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Wallet Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF18C77B), Color(0xFF13B26A))
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Top Section - User Info and Logos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = currentUser?.fullName ?: "User",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color.White.copy(alpha = 0.9f), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color(0xFFFF6400), CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Middle Section - Balance
                    Column {
                        Text(
                            text = "Your balance",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹${String.format("%.2f", currentUser?.wallet?.balance ?: 0.0)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Bottom Section - Top Up Button
                    Button(
                        onClick = { showModal = true },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Top Up",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Wallet Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                // Balance
                WalletInfoItem(
                    icon = Icons.Default.AccountBalanceWallet,
                    title = "Balance",
                    value = "₹${String.format("%.2f", currentUser?.wallet?.balance ?: 0.0)}",
                    isWarning = isLowBalance
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Low Balance Warning
                if (isLowBalance) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Minimum balance ₹$minBalance required!",
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Loyalty Points
                WalletInfoItem(
                    icon = Icons.Default.CardGiftcard,
                    title = "Loyalty Points",
                    value = "${currentUser?.wallet?.loyaltyPoints ?: 0}",
                    subtitle = getLoyaltyLevel(currentUser?.wallet?.loyaltyPoints ?: 0)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Default Payment Method
                WalletInfoItem(
                    icon = Icons.Default.CreditCard,
                    title = "Default",
                    value = getDefaultPaymentDisplay(currentUser)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Payment Methods Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment Methods",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextButton(
                    onClick = { navController.navigate(Routes.PaymentMethodsScreen) }
                ) {
                    Text(
                        text = "Manage",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Display user's actual payment methods
            currentUser?.paymentMethods?.forEach { method ->
                PaymentMethodItem(
                    method = method,
                    selected = selectedMethod == method.id,
                    onSelect = { selectedMethod = method.id },
                    isDefault = method.id == currentUser?.wallet?.defaultPaymentMethod
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Show message if no payment methods
            if (currentUser?.paymentMethods.isNullOrEmpty()) {
                Text(
                    text = "No payment methods added",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    // Add Balance Modal
    if (showModal) {
        AddBalanceModal(
            amount = amount,
            onAmountChange = { amount = it },
            selectedMethod = selectedMethod,
            onSelectedMethodChange = { selectedMethod = it },
            paymentMethods = currentUser?.paymentMethods ?: emptyList(),
            wallet = currentUser?.wallet,
            showOtherOption = showOtherOption,
            onShowOtherOptionChange = { showOtherOption = it },
            otherPaymentType = otherPaymentType,
            onOtherPaymentTypeChange = { otherPaymentType = it },
            onClose = {
                showModal = false
                showOtherOption = false
                amount = ""
                selectedMethod = currentUser?.wallet?.defaultPaymentMethod ?: ""
                otherPaymentType = ""
            },
            onProceed = {
                // Handle payment processing with actual user data
                // This would integrate with your payment gateway
                showModal = false
            }
        )
    }
}

@Composable
fun WalletInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    subtitle: String? = null,
    isWarning: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                subtitle?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isWarning) Color.Red else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    selected: Boolean,
    onSelect: () -> Unit,
    isDefault: Boolean
) {
    val displayText = when (method.type) {
        "upi" -> "UPI: ${method.upiId ?: "N/A"}"
        "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
        else -> method.type.replaceFirstChar { it.uppercase() }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                else MaterialTheme.colorScheme.surface,
                RoundedCornerShape(12.dp)
            )
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = displayText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        if (isDefault) {
            Text(
                text = "Default",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun AddBalanceModal(
    amount: String,
    onAmountChange: (String) -> Unit,
    selectedMethod: String,
    onSelectedMethodChange: (String) -> Unit,
    paymentMethods: List<PaymentMethod>,
    wallet: Wallet?,
    showOtherOption: Boolean,
    onShowOtherOptionChange: (Boolean) -> Unit,
    otherPaymentType: String,
    onOtherPaymentTypeChange: (String) -> Unit,
    onClose: () -> Unit,
    onProceed: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = if (showOtherOption) "Other Payment Method" else "Add Balance",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Amount Input
                OutlinedTextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    label = { Text("Enter Amount (₹)") },
                    placeholder = { Text("Enter amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!showOtherOption) {
                    // Payment Method Selection
                    Text(
                        text = "Select Payment Method",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Show user's actual payment methods
                    paymentMethods.forEach { method ->
                        val displayText = when (method.type) {
                            "upi" -> "UPI: ${method.upiId ?: "N/A"}"
                            "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
                            else -> method.type.replaceFirstChar { it.uppercase() }
                        } + if (method.id == wallet?.defaultPaymentMethod) " (Default)" else ""

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectedMethodChange(method.id) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .border(
                                        2.dp,
                                        if (selectedMethod == method.id) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outlineVariant,
                                        CircleShape
                                    )
                                    .background(
                                        if (selectedMethod == method.id) MaterialTheme.colorScheme.primary
                                        else Color.Transparent,
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = displayText,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Show "Other Payment Option" only if user has payment methods
                    if (paymentMethods.isNotEmpty()) {
                        // Other Payment Option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectedMethodChange("other") }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .border(
                                        2.dp,
                                        if (selectedMethod == "other") MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outlineVariant,
                                        CircleShape
                                    )
                                    .background(
                                        if (selectedMethod == "other") MaterialTheme.colorScheme.primary
                                        else Color.Transparent,
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Other Payment Method",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (selectedMethod == "other") {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { onShowOtherOptionChange(true) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Add New Payment Method")
                            }
                        }
                    } else {
                        // If no payment methods, show message
                        Text(
                            text = "No saved payment methods. Please add a payment method first.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    // Other Payment Options
                    Text(
                        text = "Select Payment Type",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    listOf(
                        "Razorpay (Credit/Debit Card, UPI, Net Banking)",
                        "PayPal",
                        "Stripe",
                        "Bank Transfer"
                    ).forEach { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOtherPaymentTypeChange(type) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .border(
                                        2.dp,
                                        if (otherPaymentType == type) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outlineVariant,
                                        CircleShape
                                    )
                                    .background(
                                        if (otherPaymentType == type) MaterialTheme.colorScheme.primary
                                        else Color.Transparent,
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = type,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    if (otherPaymentType.contains("Razorpay")) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "You will be redirected to Razorpay secure payment gateway to complete your transaction.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (showOtherOption) {
                        Button(
                            onClick = { onShowOtherOptionChange(false) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Back to Saved Methods")
                        }
                    }

                    Button(
                        onClick = onClose,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.1f),
                            contentColor = Color.Red
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = onProceed,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = amount.isNotBlank() && (
                                if (showOtherOption) otherPaymentType.isNotBlank()
                                else selectedMethod.isNotBlank() || paymentMethods.isEmpty()
                                )
                    ) {
                        Text(if (showOtherOption) "Proceed with Other Payment" else "Proceed to Pay")
                    }
                }
            }
        }
    }
}

// Helper functions
private fun getLoyaltyLevel(points: Int): String {
    return when {
        points >= 1000 -> "Platinum"
        points >= 500 -> "Gold"
        points >= 200 -> "Silver"
        else -> "New Member"
    }
}

private fun getDefaultPaymentDisplay(user: User?): String {
    val wallet = user?.wallet
    val paymentMethods = user?.paymentMethods ?: emptyList()

    val defaultMethod = paymentMethods.find { it.id == wallet?.defaultPaymentMethod }
    return defaultMethod?.let { method ->
        when (method.type) {
            "upi" -> "UPI: ${method.upiId ?: "N/A"}"
            "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
            else -> method.type.replaceFirstChar { it.uppercase() }
        }
    } ?: "None"
}