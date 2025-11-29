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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unichargeandroid.data.local.TokenManager
import com.example.unichargeandroid.data.local.UserManager
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.data.model.User
import com.example.unichargeandroid.viewmodels.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignInScreen(
    onBackClick: () -> Unit = {},
    onLoginSuccess: (String, User) -> Unit,
    onSignUpClick: () -> Unit = {},
    onAgreementsClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    val colors = MaterialTheme.colorScheme

    // Form state
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Validation state
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Observe authentication state
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle()

    // Handle authentication state changes
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                isLoading = false
            }

            is AuthState.Error -> {
                isLoading = false
            }

            else -> {}
        }
    }

    // Handle login state changes
    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthState.Authenticated -> {
                isLoading = false
                val token = TokenManager.getToken()
                val user = UserManager.getUser()
                if (token != null && user != null) {
                    onLoginSuccess(token, user)
                } else {
                    // ✅ Emit safe auth error state instead of crashing
                    passwordError = ("Session data missing, please login again")
                }
                authViewModel.resetLoginState()
            }

            is AuthState.Error -> {
                isLoading = false
                passwordError = (loginState as AuthState.Error).message
            }

            is AuthState.Loading -> {
                isLoading = true
            }

            else -> {}
        }
    }

    SideEffect {
        systemUiController.isStatusBarVisible = false
    }

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
            onValueChange = {
                email = it
                emailError = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "example@gmail.com",
                    color = colors.onBackground.copy(alpha = 0.5f)
                )
            },
            isError = emailError.isNotEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colors.surface,
                focusedContainerColor = colors.surface,
                unfocusedIndicatorColor = colors.outline.copy(alpha = 0.5f),
                focusedIndicatorColor = colors.primary,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

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
            onValueChange = {
                password = it
                passwordError = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Enter your password",
                    color = colors.onBackground.copy(alpha = 0.5f)
                )
            },
            isError = passwordError.isNotEmpty(),
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
                focusedIndicatorColor = colors.primary,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )

        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

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

        // Sign up link
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account? ",
                fontSize = 14.sp,
                color = colors.onBackground.copy(alpha = 0.7f),
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Sign up",
                fontSize = 14.sp,
                color = colors.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(
                    onClick = onSignUpClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Continue Button
        Button(
            onClick = {
                // Validate form
                if (validateSignInForm(email, password, isChecked)) {
                    // Call actual API login
                    authViewModel.login(email, password)
                } else {
                    // Show validation errors
                    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                            .matches()
                    ) {
                        emailError = "Please enter a valid email"
                    }
                    if (password.isEmpty() || password.length < 6) {
                        passwordError = "Password must be at least 6 characters"
                    }
                    if (!isChecked) {
                        // You can show agreement error
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = colors.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


private fun validateSignInForm(
    email: String,
    password: String,
    isChecked: Boolean
): Boolean {
    var isValid = true

    if (email.isEmpty()) {
        // You can set email error state here
        isValid = false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        // You can set email error state here
        isValid = false
    }

    if (password.isEmpty()) {
        // You can set password error state here
        isValid = false
    } else if (password.length < 6) {
        // You can set password error state here
        isValid = false
    }

    if (!isChecked) {
        // You can show agreement error
        isValid = false
    }

    return isValid
}