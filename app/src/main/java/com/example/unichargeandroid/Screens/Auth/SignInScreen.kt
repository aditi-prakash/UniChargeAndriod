package com.example.unichargeandroid.screens.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignInScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    onAgreementsClick: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    val colors = MaterialTheme.colorScheme

    SideEffect {
        systemUiController.isStatusBarVisible = false
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val clickablePart = "Public Agreement, Terms, Privacy Policy"
    val annotatedText = buildAnnotatedString {
        append("I agree to UniCharge ")
        pushStringAnnotation(tag = "CLICK", annotation = "AGREEMENTS")
        withStyle(style = SpanStyle(color = colors.primary)) {
            append(clickablePart)
        }
        pop()
        append(", and confirm that I am over 17 years old.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
    ) {

        Spacer(modifier = Modifier.height(45.dp))

        // Back Arrow
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colors.onBackground,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Greeting
        Text(
            text = "Hello there 👋",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please enter your email address and password for verification.",
            fontSize = 16.sp,
            color = colors.onBackground.copy(alpha = 0.7f),
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field
        Text(
            text = "Email Address",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "example@gmail.com",
                    color = colors.onBackground.copy(alpha = 0.5f)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colors.surface,
                focusedContainerColor = colors.surface,
                unfocusedIndicatorColor = colors.outline.copy(alpha = 0.5f),
                focusedIndicatorColor = colors.primary
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Password Field
        Text(
            text = "Password",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Enter your password",
                    color = colors.onBackground.copy(alpha = 0.5f)
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = colors.onBackground.copy(alpha = 0.5f)
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colors.surface,
                focusedContainerColor = colors.surface,
                unfocusedIndicatorColor = colors.outline.copy(alpha = 0.5f),
                focusedIndicatorColor = colors.primary
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Checkbox with agreements
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isChecked = !isChecked }
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = colors.primary,
                    uncheckedColor = colors.onSurface.copy(alpha = 0.5f),
                    checkmarkColor = colors.onPrimary
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = annotatedText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = colors.onBackground.copy(alpha = 0.8f),
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = onAgreementsClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Continue Button
        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}