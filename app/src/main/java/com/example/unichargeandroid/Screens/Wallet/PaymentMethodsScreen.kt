package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.data.model.PaymentMethod
import com.example.unichargeandroid.viewmodels.AuthViewModel

@Composable
fun PaymentMethodsScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val currentUser by authViewModel.currentUser.collectAsState()
    val payments = currentUser?.paymentMethods ?: emptyList()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(
                            onClick = { navController.popBackStack() },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Payment Methods",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
            }

            Spacer(Modifier.height(15.dp))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add New Payment Button
            Button(
                onClick = {
                    // Navigate to the new AddPaymentMethodScreen
                    navController.navigate(Routes.AddPaymentMethodScreen)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New Payment", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Rest of your existing PaymentMethodsScreen UI remains the same
            // Just update the onEdit action in PaymentMethodCard:
            if (payments.isEmpty()) {
                // Empty state...
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 300.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(payments) { payment ->
                        PaymentMethodCard(
                            payment = payment,
                            onEdit = {
                                // Navigate to edit screen with the payment method
                                navController.navigate(Routes.EditPaymentMethodScreen + "/${payment.id}")
                            },
                            onDelete = {
                                // Handle delete directly
                                val updatedPayments = payments.filter { it.id != payment.id }
                                updateUserPaymentMethods(authViewModel, updatedPayments, payment)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethodCard(
    payment: PaymentMethod,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (payment.type == "upi") Icons.Default.Wallet else Icons.Default.CreditCard,
                        contentDescription = "Payment Type",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (payment.type == "upi") "UPI Payment" else "Card Payment",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (payment.isDefault) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Default",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // Action Buttons
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Details
            Column {
                if (payment.type == "upi") {
                    Text(
                        text = "UPI ID: ${payment.upiId ?: "N/A"}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    payment.card?.let { card ->
                        Text(
                            text = "Card: **** **** **** ${card.cardNumberMasked}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Holder: ${card.cardHolder}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Expiry: ${card.expiryMonth}/${card.expiryYear}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } ?: run {
                        Text(
                            text = "Card details not available",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
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

        // This would typically call an API to update the user on the backend
        // For now, we'll just update the local state
        // authViewModel.updateUser(updatedUser)
    }
}