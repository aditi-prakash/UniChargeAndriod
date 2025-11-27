package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.background
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

// ------------------------------------------------------------
// DATA MODEL
// ------------------------------------------------------------
enum class PaymentType { UPI, CARD }

data class CardInfo(
    val cardNumberMasked: String = "",
    val cardHolder: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = ""
)

data class PaymentMethod(
    val id: String = "",
    val type: PaymentType = PaymentType.UPI,
    val upiId: String = "",
    val card: CardInfo = CardInfo(),
    val isDefault: Boolean = false
)

// ------------------------------------------------------------
// MAIN SCREEN
// ------------------------------------------------------------
@Composable
fun AddPaymentMethodScreen(
    method: PaymentMethod,
    onSaveClick: (PaymentMethod) -> Unit = {},
    onDeleteClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var selectedType by remember { mutableStateOf(method.type) }

    var upiId by remember { mutableStateOf(method.upiId) }
    var cardHolder by remember { mutableStateOf(method.card.cardHolder) }
    var masked by remember { mutableStateOf(method.card.cardNumberMasked) }
    var expiryMonth by remember { mutableStateOf(method.card.expiryMonth) }
    var expiryYear by remember { mutableStateOf(method.card.expiryYear) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        // ---------------- TOP BAR ----------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Edit Payment Method",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(20.dp))

        // ---------------- TYPE SELECTION ----------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Payment Type:",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // UPI Button
            Button(
                onClick = { selectedType = PaymentType.UPI },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == PaymentType.UPI)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    "UPI",
                    color = if (selectedType == PaymentType.UPI)
                        MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }

            // CARD Button
            Button(
                onClick = { selectedType = PaymentType.CARD },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == PaymentType.CARD)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    "Card",
                    color = if (selectedType == PaymentType.CARD)
                        MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------------- TYPE LABEL ----------------
        Text(
            text = if (selectedType == PaymentType.UPI) "UPI Payment" else "Card Payment",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(20.dp))

        // ---------------- INPUT FIELDS ----------------
        if (selectedType == PaymentType.UPI) {

            OutlinedTextField(
                value = upiId,
                onValueChange = { upiId = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("UPI ID", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {
                    Text(
                        "Enter UPI ID",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
            )

        } else {

            OutlinedTextField(
                value = masked,
                onValueChange = { masked = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Card Number (Masked)", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {
                    Text(
                        "**** **** **** 1234",
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
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
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = expiryMonth,
                    onValueChange = { expiryMonth = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Expiry Month", color = MaterialTheme.colorScheme.onSurface) },
                    placeholder = {
                        Text(
                            "MM",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
                )

                Spacer(Modifier.width(12.dp))

                OutlinedTextField(
                    value = expiryYear,
                    onValueChange = { expiryYear = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Expiry Year", color = MaterialTheme.colorScheme.onSurface) },
                    placeholder = {
                        Text(
                            "YY",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
        }

        Spacer(Modifier.height(26.dp))

        // ---------------- ACTION BUTTONS ----------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = { onDeleteClick(method.id) },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Delete")
            }

            Button(
                onClick = {
                    val updated = PaymentMethod(
                        id = method.id,
                        type = selectedType,
                        upiId = upiId,
                        card = CardInfo(
                            cardNumberMasked = masked,
                            cardHolder = cardHolder,
                            expiryMonth = expiryMonth,
                            expiryYear = expiryYear
                        ),
                        isDefault = method.isDefault
                    )
                    onSaveClick(updated)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Save")
            }
        }
    }
}

