package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.unichargeandroid.R
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.BottomNavBar

@Composable
fun AccountScreen(navController: NavController) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        bottomBar = { BottomNavBar(navController, Routes.AccountScreen) }
    ) { padding ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Account",
                    fontSize = 24.sp,
                    fontWeight = typography.titleLarge.fontWeight,
                    color = colors.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Profile Row (and all other AccountItem rows)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ob1),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Andrew Ainsley",
                                fontSize = 18.sp,
                                fontWeight = typography.titleMedium.fontWeight,
                                color = colors.onBackground
                            )
                            Text(
                                "+1 111 467 378 399",
                                color = colors.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = colors.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                AccountItem(iconRes = R.drawable.history, title = "History", null)
                AccountItem(iconRes = R.drawable.payment_methods, title = "Payment Methods", null)

                HorizontalDivider(
                    Modifier.padding(vertical = 12.dp),
                    color = colors.outlineVariant
                )

                AccountItem(iconRes = R.drawable.personal_info, title = "Personal Info", null)
                AccountItem(iconRes = R.drawable.security, title = "Security", null)
                AccountItemRightText(
                    iconRes = R.drawable.language,
                    title = "Language",
                    value = "English (US)"
                )
                AccountItemRightText(
                    iconRes = R.drawable.dark_mode,
                    title = "Theme",
                    value = "Auto"
                )

                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = colors.outlineVariant)

                AccountItem(
                    iconRes = R.drawable.help,
                    title = "Help Center",
                    MaterialTheme.colorScheme.onBackground
                )
                AccountItem(
                    iconRes = R.drawable.privacy_policy,
                    title = "Privacy Policy",
                    MaterialTheme.colorScheme.onBackground
                )
                AccountItem(iconRes = R.drawable.aboutev, title = "About UniCharge", null)

                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = colors.outlineVariant)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {}
                        )
                        .padding(vertical = 14.dp)
                        .padding(bottom = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Logout Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Logout",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = typography.bodyMedium.fontWeight
                    )
                }
            }
        }
    }
}

@Composable
fun AccountItem(
    iconRes: Int,
    title: String,
    iconTint: Color? = MaterialTheme.colorScheme.onBackground
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            )
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = iconTint?.let { ColorFilter.tint(it) }
        )
        Spacer(Modifier.width(16.dp))
        Text(
            title,
            fontSize = 16.sp,
            color = colors.onBackground,
            fontWeight = typography.bodyMedium.fontWeight
        )
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = colors.onSurfaceVariant
        )
    }
}

@Composable
fun AccountItemRightText(
    iconRes: Int,
    title: String,
    value: String
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            )
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            title,
            fontSize = 16.sp,
            color = colors.onBackground,
            fontWeight = typography.bodyMedium.fontWeight
        )
        Spacer(Modifier.weight(1f))
        Text(
            value,
            color = colors.onSurfaceVariant,
            fontSize = 16.sp
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = colors.onSurfaceVariant
        )
    }
}