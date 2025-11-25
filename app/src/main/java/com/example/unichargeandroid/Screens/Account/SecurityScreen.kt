package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SecurityScreen() {
    var rememberMe by remember { mutableStateOf(true) }
    var biometric by remember { mutableStateOf(false) }
    var faceId by remember { mutableStateOf(false) }
    var smsAuth by remember { mutableStateOf(false) }
    var googleAuth by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 22.dp, end = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(26.dp), tint = colors.onBackground)
            Spacer(Modifier.width(16.dp))
            Text("Security", style = typography.titleLarge, color = colors.onBackground)
        }

        Spacer(Modifier.height(28.dp))

        // Toggles
        SecurityToggleRow("Remember me", rememberMe) { rememberMe = it }
        SecurityToggleRow("Biometric ID", biometric) { biometric = it }
        SecurityToggleRow("Face ID", faceId) { faceId = it }
        SecurityToggleRow("SMS Authenticator", smsAuth) { smsAuth = it }
        SecurityToggleRow("Google Authenticator", googleAuth) { googleAuth = it }

        Spacer(Modifier.height(20.dp))

        // Device Management Row
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Device Management", style = typography.titleMedium, color = colors.onBackground)
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Navigate", tint = colors.onSurfaceVariant)
        }

        Spacer(Modifier.height(20.dp))

        // Change Password Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colors.primary.copy(alpha = 0.15f), RoundedCornerShape(22.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Change Password",
                color = colors.primary,
                style = typography.titleMedium
            )
        }
    }
}

@Composable
fun SecurityToggleRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = typography.titleMedium, color = colors.onBackground)
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.primary,
                checkedTrackColor = colors.primary.copy(alpha = 0.25f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecurityScreenMTU() {
    MaterialTheme {
        SecurityScreen()
    }
}
