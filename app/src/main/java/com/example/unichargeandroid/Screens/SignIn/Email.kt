package com.example.unichargeandroid.screens.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun EmailSignInScreenMT() {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }

    val clickablePart = "Public Agreement, Terms, Privacy Policy"
    val annotatedText = buildAnnotatedString {
        append("I agree to UniCharge ")
        pushStringAnnotation(tag = "CLICK", annotation = "AGREEMENTS")
        withStyle(style = SpanStyle(color = Color(0xFF18C77B))) {
            append(clickablePart)
        }
        pop()
        append(", and confirm that I am over 17 years old.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // Back Arrow
        IconButton(
            onClick = { /* handle back */ },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colors.onBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Greeting
        Text(
            text = "Hello there 👋",
            style = typography.headlineMedium,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please enter your email address and password for verification.",
            style = typography.bodyMedium,
            color = colors.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        Text(
            text = "Email Address",
            fontWeight = FontWeight.SemiBold,
            style = typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("example@gmail.com") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Password Field
        Text(
            text = "Password",
            fontWeight = FontWeight.SemiBold,
            style = typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter your password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Checkbox with agreements
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF18C77B),
                    uncheckedColor = Color(0xFF18C77B),
                    checkmarkColor = Color.White
                )
            )

            Text(
                text = annotatedText,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { /* open agreements */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Continue Button
        Button(
            onClick = { /* Continue */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF18C77B))
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                style = typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailSignInScreenMT() {
    MaterialTheme {
        EmailSignInScreenMT()
    }
}
