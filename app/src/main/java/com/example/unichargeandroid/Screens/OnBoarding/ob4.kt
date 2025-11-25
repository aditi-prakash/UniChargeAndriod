package com.example.unichargeandroid.Screens.OnBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R

@Composable
fun LoginScreenAdaptive() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // Proportional sizing
    val imageHeight = screenHeight * 0.25f
    val topSpacer = screenHeight * 0.04f
    val titleSpacer = screenHeight * 0.02f
    val buttonHeight = screenHeight * 0.065f
    val elementSpacer = screenHeight * 0.015f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(topSpacer))

        // Top Illustration
        Image(
            painter = painterResource(id = R.drawable.ob4),
            contentDescription = "Login Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(titleSpacer))

        // Title
        Text(
            text = "Let's you in",
            fontSize = 26.sp,
            color = colors.onBackground,
            style = typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(elementSpacer))

        // Google Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .background(colors.surfaceVariant, RoundedCornerShape(30.dp))
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(buttonHeight * 0.5f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Continue with Google",
                fontSize = 16.sp,
                color = colors.onSurfaceVariant,
                style = typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(elementSpacer))

        // OR Divider
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = colors.outline.copy(alpha = 0.5f),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "  or  ",
                fontSize = 14.sp,
                color = colors.onBackground.copy(alpha = 0.6f)
            )
            Divider(
                color = colors.outline.copy(alpha = 0.5f),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(elementSpacer))

        // Sign in with Phone Number
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Text(
                text = "Sign in with Phone Number",
                fontSize = 16.sp,
                style = typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(elementSpacer))

        // Signup Text
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account? ",
                fontSize = 14.sp,
                color = colors.onBackground.copy(alpha = 0.6f),
                style = typography.bodyMedium
            )
            Text(
                text = "Sign up",
                fontSize = 14.sp,
                color = colors.primary,
                style = typography.bodyMedium,
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreenAdaptive() {
    MaterialTheme {
        LoginScreenAdaptive()
    }
}
