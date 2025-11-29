package com.example.unichargeandroid.Screens.Wallet
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.ui.tooling.preview.Preview
//
//// ------------------------------------------------------------
//// MAIN SCREEN (Only this file’s content)
//// ------------------------------------------------------------
//@Composable
//fun EditPaymentMethodScreen(
//    method: PaymentMethod,
//    onSaveClick: (PaymentMethod) -> Unit = {},
//    onDeleteClick: (String) -> Unit = {},
//    onBackClick: () -> Unit = {}
//) {
//
//    val selectedType = method.type
//
//    var upiId by remember { mutableStateOf(method.upiId) }
//    var cardHolder by remember { mutableStateOf(method.card.cardHolder) }
//    var masked by remember { mutableStateOf(method.card.cardNumberMasked) }
//    var expiryMonth by remember { mutableStateOf(method.card.expiryMonth) }
//    var expiryYear by remember { mutableStateOf(method.card.expiryYear) }
//
//    Column(
//        Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(16.dp)
//    ) {
//        // Top Bar
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            IconButton(onClick = onBackClick) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back",
//                    tint = MaterialTheme.colorScheme.onBackground
//                )
//            }
//            Text(
//                text = "Edit Payment Method",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//
//        Spacer(Modifier.height(20.dp))
//
//        // Title based on type
//        Text(
//            text = if (selectedType == PaymentType.UPI) "UPI Payment" else "Card Payment",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = MaterialTheme.colorScheme.primary
//        )
//
//        Spacer(Modifier.height(20.dp))
//
//        // Fields
//        if (selectedType == PaymentType.UPI) {
//
//            OutlinedTextField(
//                value = upiId,
//                onValueChange = { upiId = it },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("UPI ID") },
//                placeholder = { Text("Enter UPI ID") },
//                textStyle = LocalTextStyle.current.copy(
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            )
//
//        } else {
//
//            OutlinedTextField(
//                value = masked,
//                onValueChange = { masked = it },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("Masked Card Number") },
//                placeholder = { Text("**** **** **** 1234") },
//                textStyle = LocalTextStyle.current.copy(
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            )
//
//            Spacer(Modifier.height(14.dp))
//
//            OutlinedTextField(
//                value = cardHolder,
//                onValueChange = { cardHolder = it },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("Card Holder Name") },
//                placeholder = { Text("Enter card holder name") },
//                textStyle = LocalTextStyle.current.copy(
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            )
//
//            Spacer(Modifier.height(14.dp))
//
//            Row(Modifier.fillMaxWidth()) {
//                OutlinedTextField(
//                    value = expiryMonth,
//                    onValueChange = { expiryMonth = it },
//                    modifier = Modifier.weight(1f),
//                    label = { Text("Expiry Month") },
//                    placeholder = { Text("MM") },
//                    textStyle = LocalTextStyle.current.copy(
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                )
//
//                Spacer(Modifier.width(12.dp))
//
//                OutlinedTextField(
//                    value = expiryYear,
//                    onValueChange = { expiryYear = it },
//                    modifier = Modifier.weight(1f),
//                    label = { Text("Expiry Year") },
//                    placeholder = { Text("YY") },
//                    textStyle = LocalTextStyle.current.copy(
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                )
//            }
//        }
//
//        Spacer(Modifier.height(26.dp))
//
//        // Buttons
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Button(
//                onClick = { onDeleteClick(method.id) },
//                modifier = Modifier
//                    .weight(1f)
//                    .height(52.dp),
//                shape = RoundedCornerShape(50.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.error,
//                    contentColor = MaterialTheme.colorScheme.onError
//                )
//            ) {
//                Text("Delete", textAlign = TextAlign.Center)
//            }
//
//            Button(
//                onClick = {
//                    val updated = PaymentMethod(
//                        id = method.id,
//                        type = selectedType,
//                        upiId = upiId,
//                        card = CardInfo(
//                            cardNumberMasked = masked,
//                            cardHolder = cardHolder,
//                            expiryMonth = expiryMonth,
//                            expiryYear = expiryYear
//                        ),
//                        isDefault = method.isDefault
//                    )
//                    onSaveClick(updated)
//                },
//                modifier = Modifier
//                    .weight(1f)
//                    .height(52.dp),
//                shape = RoundedCornerShape(50.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            ) {
//                Text("Save", textAlign = TextAlign.Center)
//            }
//        }
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun EditPayment_Card_Dark() {
//    MaterialTheme(colorScheme = darkColorScheme()) {
//        EditPaymentMethodScreen(
//            method = PaymentMethod(
//                type = PaymentType.CARD,
//                card = CardInfo(
//                    cardNumberMasked = "",
//                    cardHolder = "",
//                    expiryMonth = "",
//                    expiryYear = ""
//                )
//            )
//        )
//    }
//}