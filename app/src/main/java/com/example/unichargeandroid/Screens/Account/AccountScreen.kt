package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
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
                .padding(horizontal = 30.dp)
                .padding(top = 15.dp)
                .fillMaxSize()
                .background(colors.background)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .background(colors.background)
            ) {

                // Profile Row (and all other AccountItem rows)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.background)
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

                        IconButton(onClick = { navController.navigate(Routes.EditProfileScreen) }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = colors.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                AccountItem(
                    iconRes = R.drawable.history,
                    title = "History",
                    null,
                    onClick = { navController.navigate(Routes.HistoryScreen) }
                )
                AccountItem(
                    iconRes = R.drawable.payment_methods, title = "Payment Methods", null,
                    onClick = { navController.navigate(Routes.AboutScreen) }
                )

                HorizontalDivider(
                    Modifier.padding(vertical = 12.dp),
                    color = colors.outlineVariant
                )

                AccountItem(
                    iconRes = R.drawable.personal_info,
                    title = "Personal Info",
                    null,
                    onClick = { navController.navigate(Routes.AboutScreen) }
                )
                AccountItem(
                    iconRes = R.drawable.security,
                    title = "Security",
                    null,
                    onClick = { navController.navigate(Routes.SecurityScreen) }
                )
                AccountItemRightText(
                    iconRes = R.drawable.language,
                    title = "Language",
                    value = "English (US)",
                    onClick = { navController.navigate(Routes.LanguageScreen) }
                )
                AccountItemRightText(
                    iconRes = R.drawable.dark_mode,
                    title = "Theme",
                    value = "Auto",
                    onClick = { navController.navigate(Routes.ThemeScreen) }
                )

                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = colors.outlineVariant)

                AccountItem(
                    iconRes = R.drawable.help,
                    title = "Help Center",
                    MaterialTheme.colorScheme.onBackground,
                    onClick = { navController.navigate(Routes.HelpCenterScreen) }
                )
                AccountItem(
                    iconRes = R.drawable.privacy_policy,
                    title = "Privacy Policy",
                    MaterialTheme.colorScheme.onBackground,
                    onClick = { navController.navigate(Routes.PrivacyPolicyScreen) }
                )
                AccountItem(
                    iconRes = R.drawable.unicharge_logo_rounded,
                    title = "About UniCharge",
                    null,
                    onClick = { navController.navigate(Routes.AboutScreen) }
                )

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
    iconTint: Color? = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
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
    value: String,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
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