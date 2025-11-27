package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.unichargeandroid.data.model.CardDetails
import com.example.unichargeandroid.data.model.FormData
import com.example.unichargeandroid.data.model.PaymentMethod


@Composable
fun PaymentMethodsScreen(navController: NavController) {
    var payments by remember { mutableStateOf<List<PaymentMethod>>(emptyList()) }
    var showModal by remember { mutableStateOf(false) }
    var editingPayment by remember { mutableStateOf<PaymentMethod?>(null) }
    var formData by remember { mutableStateOf(createEmptyFormData()) }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Sample data - replace with actual API call
    LaunchedEffect(Unit) {
        payments = listOf(
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
    }

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
                    colorFilter = ColorFilter.tint(colors.onBackground)
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
                    editingPayment = null
                    formData = createEmptyFormData()
                    showModal = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(40.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New Payment", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Payment Methods Grid
            if (payments.isEmpty()) {
                // Empty State
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No payment methods added yet.",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Click \"Add New Payment\" to get started!",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
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
                                editingPayment = payment
                                formData = FormData(
                                    type = payment.type,
                                    upiId = payment.upiId ?: "",
                                    card = payment.card ?: CardDetails("", "", "", ""),
                                    isDefault = payment.isDefault
                                )
                                showModal = true
                            },
                            onDelete = {
                                // Handle delete
                                payments = payments.filter { it.id != payment.id }
                            }
                        )
                    }
                }
            }
        }
    }

    // Add/Edit Payment Modal
    if (showModal) {
        AddEditPaymentModal(
            formData = formData,
            onFormDataChange = { formData = it },
            editingPayment = editingPayment,
            onClose = {
                showModal = false
                editingPayment = null
                formData = createEmptyFormData()
            },
            onSave = { updatedFormData ->
                if (editingPayment != null) {
                    // Update existing payment
                    payments = payments.map { payment ->
                        if (payment.id == editingPayment!!.id) {
                            payment.copy(
                                type = updatedFormData.type,
                                upiId = if (updatedFormData.type == "upi") updatedFormData.upiId else null,
                                card = if (updatedFormData.type == "card") updatedFormData.card else null,
                                isDefault = updatedFormData.isDefault
                            )
                        } else {
                            if (updatedFormData.isDefault) payment.copy(isDefault = false) else payment
                        }
                    }
                } else {
                    // Add new payment
                    val newPayment = PaymentMethod(
                        id = (payments.size + 1).toString(),
                        type = updatedFormData.type,
                        upiId = if (updatedFormData.type == "upi") updatedFormData.upiId else null,
                        card = if (updatedFormData.type == "card") updatedFormData.card else null,
                        isDefault = updatedFormData.isDefault
                    )
                    payments = if (updatedFormData.isDefault) {
                        listOf(newPayment) + payments.map { it.copy(isDefault = false) }
                    } else {
                        payments + newPayment
                    }
                }
                showModal = false
                editingPayment = null
                formData = createEmptyFormData()
            }
        )
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
                        text = "UPI ID: ${payment.upiId}",
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
                    }
                }
            }

        }
    }
}

@Composable
fun AddEditPaymentModal(
    formData: FormData,
    onFormDataChange: (FormData) -> Unit,
    editingPayment: PaymentMethod?,
    onClose: () -> Unit,
    onSave: (FormData) -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = if (editingPayment != null) "Edit Payment" else "Add Payment",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Payment Type Selector
                Text(
                    text = "Payment Type",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PaymentTypeOption(
                        type = "upi",
                        selected = formData.type == "upi",
                        onClick = { onFormDataChange(formData.copy(type = "upi")) }
                    )
                    PaymentTypeOption(
                        type = "card",
                        selected = formData.type == "card",
                        onClick = { onFormDataChange(formData.copy(type = "card")) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // UPI Fields
                if (formData.type == "upi") {
                    OutlinedTextField(
                        value = formData.upiId,
                        onValueChange = { onFormDataChange(formData.copy(upiId = it)) },
                        label = { Text("UPI ID") },
                        placeholder = { Text("example@upi") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                // Card Fields
                if (formData.type == "card") {
                    OutlinedTextField(
                        value = formData.card.cardNumberMasked,
                        onValueChange = {
                            if (it.length <= 4) {
                                onFormDataChange(formData.copy(
                                    card = formData.card.copy(cardNumberMasked = it)
                                ))
                            }
                        },
                        label = { Text("Card Number (Last 4)") },
                        placeholder = { Text("1234") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = formData.card.cardHolder,
                        onValueChange = {
                            onFormDataChange(formData.copy(
                                card = formData.card.copy(cardHolder = it)
                            ))
                        },
                        label = { Text("Card Holder") },
                        placeholder = { Text("John Doe") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = formData.card.expiryMonth,
                            onValueChange = {
                                if (it.length <= 2) {
                                    onFormDataChange(formData.copy(
                                        card = formData.card.copy(expiryMonth = it)
                                    ))
                                }
                            },
                            label = { Text("Expiry Month") },
                            placeholder = { Text("MM") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = formData.card.expiryYear,
                            onValueChange = {
                                if (it.length <= 2) {
                                    onFormDataChange(formData.copy(
                                        card = formData.card.copy(expiryYear = it)
                                    ))
                                }
                            },
                            label = { Text("Expiry Year") },
                            placeholder = { Text("YY") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Default Payment Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onFormDataChange(formData.copy(isDefault = !formData.isDefault))
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = formData.isDefault,
                        onCheckedChange = {
                            onFormDataChange(formData.copy(isDefault = it))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Set as Default",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onClose,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { onSave(formData) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = isFormValid(formData)
                    ) {
                        Text(if (editingPayment != null) "Save Changes" else "Add Payment")
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentTypeOption(
    type: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Box(
        modifier = Modifier
//            .weight(1f)
            .height(48.dp)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .background(containerColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (type == "upi") "UPI" else "Card",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}

fun createEmptyFormData(): FormData {
    return FormData(
        type = "upi",
        upiId = "",
        card = CardDetails("", "", "", ""),
        isDefault = false
    )
}

private fun isFormValid(formData: FormData): Boolean {
    return when (formData.type) {
        "upi" -> formData.upiId.isNotBlank()
        "card" -> formData.card.cardNumberMasked.isNotBlank() &&
                formData.card.cardHolder.isNotBlank() &&
                formData.card.expiryMonth.isNotBlank() &&
                formData.card.expiryYear.isNotBlank()
        else -> false
    }
}