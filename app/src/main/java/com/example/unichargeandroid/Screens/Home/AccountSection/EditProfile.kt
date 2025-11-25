package com.example.unichargeandroid.Screens.Home.AccountSection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PersonalInfoScreenUI() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 40.dp, start = 18.dp, end = 18.dp, bottom = 20.dp)
    ) {

        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colors.onBackground,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(14.dp))
            Text(
                "Personal Info",
                fontSize = 20.sp,
                fontWeight = typography.titleLarge.fontWeight,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(24.dp))

        // Full Name
        InfoLabel("Full Name")
        UnderlineText(fullName, "Enter full name")

        Spacer(Modifier.height(16.dp))

        // Phone Number
        InfoLabel("Phone Number")
        UnderlineText(phone, "Enter phone number")

        Spacer(Modifier.height(16.dp))

        // Email
        InfoLabel("Email")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UnderlineText(email, "Enter email", Modifier.weight(1f))
            Text(
                "Verify",
                color = colors.primary,
                fontWeight = typography.labelMedium.fontWeight,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(Modifier.height(16.dp))

        // Gender
        InfoLabel("Gender")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnderlineText(gender, "Select gender", Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = colors.onSurface)
        }

        Spacer(Modifier.height(16.dp))

        // DOB
        InfoLabel("Date of Birth")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnderlineText(dob, "Select date", Modifier.weight(1f))
            Icon(Icons.Default.DateRange, contentDescription = null, tint = colors.onSurface)
        }

        Spacer(Modifier.height(16.dp))

        // Address
        InfoLabel("Street Address")
        UnderlineText(address, "Enter street address")

        Spacer(Modifier.height(16.dp))

        // Country
        InfoLabel("Country")
        UnderlineText(country, "Enter country")

        Spacer(Modifier.height(24.dp))

        // Continue Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Continue", style = typography.labelLarge, color = colors.onPrimary)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun InfoLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun UnderlineText(value: String, placeholder: String, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (value.isEmpty()) placeholder else value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (value.isEmpty()) colors.onSurface.copy(alpha = 0.6f) else colors.onSurface
        )
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.primary)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPersonalInfoScreenUI() {
    MaterialTheme {
        PersonalInfoScreenUI()
    }
}
