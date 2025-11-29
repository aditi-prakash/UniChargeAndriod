package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unichargeandroid.data.model.CardDetails
import com.example.unichargeandroid.data.model.PaymentMethod
import com.example.unichargeandroid.viewmodels.AuthViewModel

@Composable
fun AddPaymentMethodScreen(
    authViewModel: AuthViewModel = viewModel(),
    editingPayment: PaymentMethod? = null,
    onBackClick: () -> Unit = {}
) {
    // Initialize state with existing payment method data or empty values
    var selectedType by remember { mutableStateOf(editingPayment?.type ?: "upi") }
    var upiId by remember { mutableStateOf(editingPayment?.upiId ?: "") }
    var cardHolder by remember { mutableStateOf(editingPayment?.card?.cardHolder ?: "") }
    var cardNumberMasked by remember { mutableStateOf(editingPayment?.card?.cardNumberMasked ?: "") }
    var expiryMonth by remember { mutableStateOf(editingPayment?.card?.expiryMonth ?: "") }
    var expiryYear by remember { mutableStateOf(editingPayment?.card?.expiryYear ?: "") }
    var isDefault by remember { mutableStateOf(editingPayment?.isDefault ?: false) }

    val currentUser by authViewModel.currentUser.collectAsState()
    val existingPayments = currentUser?.paymentMethods ?: emptyList()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
    ) {
        // ---------------- TOP BAR ----------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        onClick = { onBackClick },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = if (editingPayment != null) "Edit Payment Method" else "Add Payment Method",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(20.dp))

        // ---------------- TYPE SELECTION ----------------
        Text(
            "Payment Type:",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // UPI Button
            Button(
                onClick = { selectedType = "upi" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "upi")
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "UPI",
                    color = if (selectedType == "upi")
                        MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }

            // CARD Button
            Button(
                onClick = { selectedType = "card" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "card")
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Card",
                    color = if (selectedType == "card")
                        MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // ---------------- TYPE LABEL ----------------
        Text(
            text = if (selectedType == "upi") "UPI Payment" else "Card Payment",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(20.dp))

        // ---------------- INPUT FIELDS ----------------
        if (selectedType == "upi") {
            OutlinedTextField(
                value = upiId,
                onValueChange = { upiId = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("UPI ID", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {
                    Text(
                        "example@oksbi",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true
            )
        } else {
            OutlinedTextField(
                value = cardNumberMasked,
                onValueChange = {
                    if (it.length <= 4) {
                        cardNumberMasked = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Card Number (Last 4 digits)", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {
                    Text(
                        "1234",
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = cardHolder,
                onValueChange = { cardHolder = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Card Holder Name", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {
                    Text(
                        "Enter card holder name",
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true
            )

            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = expiryMonth,
                    onValueChange = {
                        if (it.length <= 2) {
                            expiryMonth = it
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Expiry Month", color = MaterialTheme.colorScheme.onSurface) },
                    placeholder = {
                        Text(
                            "MM",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = expiryYear,
                    onValueChange = {
                        if (it.length <= 2) {
                            expiryYear = it
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Expiry Year", color = MaterialTheme.colorScheme.onSurface) },
                    placeholder = {
                        Text(
                            "YY",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------------- DEFAULT PAYMENT CHECKBOX ----------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isDefault = !isDefault },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isDefault,
                onCheckedChange = { isDefault = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Set as default payment method",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(26.dp))

        // ---------------- ACTION BUTTONS ----------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (editingPayment != null) {
                Button(
                    onClick = {
                        // Handle delete
                        val updatedPayments = existingPayments.filter { it.id != editingPayment.id }
                        updateUserPaymentMethods(authViewModel, updatedPayments, editingPayment)
                        onBackClick
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Delete")
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Button(
                onClick = {
                    val paymentMethod = PaymentMethod(
                        id = editingPayment?.id ?: generateNewPaymentMethodId(existingPayments),
                        type = selectedType,
                        upiId = if (selectedType == "upi") upiId else null,
                        card = if (selectedType == "card") CardDetails(
                            cardNumberMasked = cardNumberMasked,
                            cardHolder = cardHolder,
                            expiryMonth = expiryMonth,
                            expiryYear = expiryYear
                        ) else null,
                        isDefault = isDefault
                    )

                    // Update user's payment methods
                    val updatedPayments = if (editingPayment != null) {
                        // Update existing
                        existingPayments.map {
                            if (it.id == editingPayment.id) paymentMethod
                            else if (isDefault) it.copy(isDefault = false)
                            else it
                        }
                    } else {
                        // Add new
                        if (isDefault) {
                            listOf(paymentMethod) + existingPayments.map { it.copy(isDefault = false) }
                        } else {
                            existingPayments + paymentMethod
                        }
                    }

                    updateUserPaymentMethods(authViewModel, updatedPayments, paymentMethod)
                    onBackClick
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = isFormValid(selectedType, upiId, cardNumberMasked, cardHolder, expiryMonth, expiryYear)
            ) {
                Text(if (editingPayment != null) "Save Changes" else "Add Payment")
            }
        }
    }
}

// Helper functions (keep these from previous implementation)
private fun isFormValid(
    type: String,
    upiId: String,
    cardNumberMasked: String,
    cardHolder: String,
    expiryMonth: String,
    expiryYear: String
): Boolean {
    return when (type) {
        "upi" -> upiId.isNotBlank() && upiId.contains('@')
        "card" -> cardNumberMasked.isNotBlank() &&
                cardHolder.isNotBlank() &&
                expiryMonth.isNotBlank() &&
                expiryYear.isNotBlank() &&
                cardNumberMasked.length == 4
        else -> false
    }
}

private fun generateNewPaymentMethodId(existingPayments: List<PaymentMethod>): String {
    val maxId = existingPayments.maxByOrNull { it.id.toIntOrNull() ?: 0 }?.id?.toIntOrNull() ?: 0
    return (maxId + 1).toString()
}

private fun updateUserPaymentMethods(
    authViewModel: AuthViewModel,
    updatedPayments: List<PaymentMethod>,
    updatedPayment: PaymentMethod? = null
) {
    val currentUser = authViewModel.currentUser.value
    if (currentUser != null) {
        // Update wallet's default payment method if needed
        val updatedWallet = if (updatedPayment?.isDefault == true) {
            currentUser.wallet.copy(defaultPaymentMethod = updatedPayment.id)
        } else {
            currentUser.wallet
        }

        val updatedUser = currentUser.copy(
            paymentMethods = updatedPayments,
            wallet = updatedWallet
        )

        // Update in ViewModel (this would typically call an API)
        // authViewModel.updateUser(updatedUser)
    }
}