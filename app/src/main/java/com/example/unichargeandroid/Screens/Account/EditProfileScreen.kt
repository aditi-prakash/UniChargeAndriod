package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Pre-filled with user data (you can replace with actual user data)
    var fullName by remember { mutableStateOf("Andrew Ainsley") }
    var phone by remember { mutableStateOf("+1 111 467 378 399") }
    var email by remember { mutableStateOf("andrew.ainsley@example.com") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 50.dp, start = 18.dp, end = 18.dp, bottom = 20.dp)
            .background(colors.background)
    ) {

        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        onClick = onBackClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                colorFilter = ColorFilter.tint(colors.onBackground)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                "Edit Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(24.dp))

        // Full Name - Editable
        InfoLabel("Full Name")
        EditableUnderlineText(
            value = fullName,
            placeholder = "Enter full name",
            onValueChange = { fullName = it }
        )

        Spacer(Modifier.height(16.dp))

        // Phone Number - Editable
        InfoLabel("Phone Number")
        EditableUnderlineText(
            value = phone,
            placeholder = "Enter phone number",
            onValueChange = { phone = it }
        )

        Spacer(Modifier.height(16.dp))

        // Email - Non-editable
        InfoLabel("Email")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 24.dp), // Set minimum height
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NonEditableUnderlineText(
                value = email,
                placeholder = "Enter email",
                modifier = Modifier.weight(1f)
            )
            Text(
                "Verified",
                color = colors.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Continue Button
        Button(
            onClick = {
                // Handle save profile logic here
                // You can save the updated fullName and phone values
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text("Save Changes", style = typography.labelLarge, color = colors.onPrimary)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun InfoLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun EditableUnderlineText(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = colors.primary,
                unfocusedIndicatorColor = colors.outline,
                cursorColor = colors.primary,
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface
            ),
            singleLine = true
        )
    }
}

@Composable
fun NonEditableUnderlineText(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (value.isEmpty()) placeholder else value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (value.isEmpty()) colors.onSurface.copy(alpha = 0.6f) else colors.onSurface.copy(alpha = 0.8f)
        )
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.outline)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    MaterialTheme {
        EditProfileScreen()
    }
}