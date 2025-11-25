package com.example.unichargeandroid.Screens.Home.AccountSection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LanguageScreenUI() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var selected by remember { mutableStateOf("English (US)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 22.dp, end = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = colors.onBackground, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(16.dp))
            Text("Language", style = typography.titleLarge, color = colors.onBackground)
        }

        Spacer(Modifier.height(30.dp))

        // Suggested label
        Text(
            "Suggested",
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant
        )

        Spacer(Modifier.height(20.dp))

        // Language options
        LanguageRowItem(
            text = "English (US)",
            selected = selected == "English (US)",
            onClick = { selected = "English (US)" }
        )

        LanguageRowItem(
            text = "English (UK)",
            selected = selected == "English (UK)",
            onClick = { selected = "English (UK)" }
        )

        // Divider under suggested
        Spacer(Modifier.height(10.dp))
        Divider(color = colors.surfaceVariant, thickness = 1.dp)
    }
}

@Composable
fun LanguageRowItem(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = typography.bodyLarge, color = colors.onSurface)

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageScreenUI() {
    MaterialTheme {
        LanguageScreenUI()
    }
}
