package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R

@Composable
fun ContactUsScreen() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 18.dp, end = 18.dp)
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
                "Help Center",
                fontSize = 20.sp,
                fontWeight = typography.titleLarge.fontWeight,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(24.dp))

        // Tabs Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "FAQ",
                fontSize = 16.sp,
                color = colors.onSurfaceVariant
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Contact us",
                    fontSize = 16.sp,
                    fontWeight = typography.titleMedium.fontWeight,
                    color = colors.primary
                )
                Spacer(Modifier.height(4.dp))
                Box(
                    Modifier
                        .height(2.dp)
                        .width(70.dp)
                        .background(colors.primary, RoundedCornerShape(1.dp))
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Contact Cards - reduced height to fit
        ContactItem("Contact us", R.drawable.contact)
        Spacer(Modifier.height(12.dp))
        ContactItem("WhatsApp", R.drawable.whatsapp)
        Spacer(Modifier.height(12.dp))
        ContactItem("Instagram", R.drawable.instagram)
        Spacer(Modifier.height(12.dp))
        ContactItem("Facebook", R.drawable.facebook)
        Spacer(Modifier.height(12.dp))
        ContactItem("Twitter", R.drawable.twitter)
        Spacer(Modifier.height(12.dp))
        ContactItem("Website", R.drawable.website)
    }
}

@Composable
fun ContactItem(title: String, iconRes: Int) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp) // reduced from 60dp
            .clip(RoundedCornerShape(14.dp)) // slightly smaller radius
            .background(colors.surface)
            .padding(horizontal = 16.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp) // slightly smaller icon
        )

        Spacer(Modifier.width(14.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = typography.bodyMedium.fontWeight,
            color = colors.onSurface
        )
    }
}