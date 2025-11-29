package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoutCard(
    onConfirmLogout: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface, RoundedCornerShape(28.dp))
            .padding(horizontal = 26.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Logout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Are you sure you want to log out?",
            fontSize = 16.sp,
            color = colors.onSurface
        )

        Spacer(Modifier.height(26.dp))

        // CANCEL button
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.primary
            )
        ) {
            Text(
                text = "Cancel",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(14.dp))

        // YES, LOGOUT button
        Button(
            onClick = onConfirmLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Text(
                text = "Yes, Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogoutCard() {
    LogoutCard()
}
