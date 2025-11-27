package com.example.unichargeandroid.Screens.Wallet

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.BottomNavBar
import com.example.unichargeandroid.data.model.CardDetails
import com.example.unichargeandroid.data.model.PaymentMethod
import com.example.unichargeandroid.data.model.WalletData

@Composable
fun WalletScreen(navController: NavController) {
    var wallet by remember { mutableStateOf(WalletData()) }
    var paymentMethods by remember { mutableStateOf<List<PaymentMethod>>(emptyList()) }
    var showModal by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("") }
    var showOtherOption by remember { mutableStateOf(false) }
    var otherPaymentType by remember { mutableStateOf("") }

    // Sample payment methods
    val samplePaymentMethods = listOf(
        PaymentMethod(
            id = "1",
            type = "upi",
            upiId = "andrew@oksbi",
            isDefault = true
        ),
        PaymentMethod(
            id = "2",
            type = "card",
            card = CardDetails(
                cardNumberMasked = "1234",
                cardHolder = "Andrew Ainsley",
                expiryMonth = "12",
                expiryYear = "25"
            ),
            isDefault = false
        )
    )

    LaunchedEffect(Unit) {
        // Simulate API call to fetch wallet data
        paymentMethods = samplePaymentMethods
        wallet = wallet.copy(defaultPaymentMethod = samplePaymentMethods.firstOrNull()?.id)
    }

    val scrollState = rememberScrollState()
    val minBalance = 500.0
    val isLowBalance = wallet.balance < minBalance

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
                                text = "Andrew Ainsley",
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
                            text = "₹${wallet.balance}",
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
                    value = "₹${wallet.balance}",
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
                    value = "${wallet.loyaltyPoints}",
                    subtitle = getLoyaltyLevel(wallet.loyaltyPoints)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Default Payment Method
                WalletInfoItem(
                    icon = Icons.Default.CreditCard,
                    title = "Default",
                    value = getDefaultPaymentDisplay(wallet, paymentMethods)
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

            paymentMethods.forEach { method ->
                PaymentMethodItem(
                    method = method,
                    selected = selectedMethod == method.id,
                    onSelect = { selectedMethod = method.id }
                )
                Spacer(modifier = Modifier.height(8.dp))
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
            paymentMethods = paymentMethods,
            wallet = wallet,
            showOtherOption = showOtherOption,
            onShowOtherOptionChange = { showOtherOption = it },
            otherPaymentType = otherPaymentType,
            onOtherPaymentTypeChange = { otherPaymentType = it },
            onClose = {
                showModal = false
                showOtherOption = false
                amount = ""
                selectedMethod = wallet.defaultPaymentMethod ?: ""
                otherPaymentType = ""
            },
            onProceed = {
                // Handle payment processing
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
    onSelect: () -> Unit
) {
    val displayText = when (method.type) {
        "upi" -> "UPI: ${method.upiId}"
        "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
        else -> method.type
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
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
            color = MaterialTheme.colorScheme.onSurface
        )

        if (method.id == getDefaultPaymentMethodId()) {
            Spacer(modifier = Modifier.weight(1f))
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
    wallet: WalletData,
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

                    paymentMethods.forEach { method ->
                        val displayText = when (method.type) {
                            "upi" -> "UPI: ${method.upiId}"
                            "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
                            else -> method.type
                        } + if (method.id == wallet.defaultPaymentMethod) " (Default)" else ""

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
                                else selectedMethod.isNotBlank()
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

private fun getDefaultPaymentDisplay(
    wallet: WalletData,
    paymentMethods: List<PaymentMethod>
): String {
    val defaultMethod = paymentMethods.find { it.id == wallet.defaultPaymentMethod }
    return defaultMethod?.let { method ->
        when (method.type) {
            "upi" -> "UPI: ${method.upiId}"
            "card" -> "Card: **** ${method.card?.cardNumberMasked ?: "XXXX"}"
            else -> method.type
        }
    } ?: "None"
}

private fun getDefaultPaymentMethodId(): String {
    // This would come from your actual data source
    return "1"
}