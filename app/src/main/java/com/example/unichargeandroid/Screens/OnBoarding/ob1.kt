package com.example.unichargeandroid.Screens.OnBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen1() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Main Image
        Image(
            painter = painterResource(id = R.drawable.ob1),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // Reduced height to fit screen
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Title
        Text(
            text = "Easily find EV charging\nstations around you",
            fontSize = 24.sp,
            fontWeight = typography.titleLarge.fontWeight ?: androidx.compose.ui.text.font.FontWeight.Bold,
            color = colors.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp,
            style = typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle
        Text(
            text = "Discover charging points near you in a few taps. Navigate, book, and manage your EV charging efficiently.",
            fontSize = 14.sp,
            color = colors.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            style = typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Page Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(6.dp)
                    .background(colors.primary, RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(colors.onSurface.copy(alpha = 0.3f), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(colors.onSurface.copy(alpha = 0.3f), CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Skip
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.secondaryContainer,
                    contentColor = colors.primary
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Skip", fontSize = 16.sp, fontWeight = typography.labelLarge.fontWeight)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Next
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Next", fontSize = 16.sp, fontWeight = typography.labelLarge.fontWeight)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnBoardingScreen1() {
    MaterialTheme {
        OnBoardingScreen1()
    }
}
