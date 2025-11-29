package com.example.unichargeandroid.Screens.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unichargeandroid.data.model.AuthState
import com.example.unichargeandroid.viewmodels.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit,
    onSignInClick: () -> Unit = {},
    onAgreementsClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    val colors = MaterialTheme.colorScheme

    // Form state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // Validation state
    var fullNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Observe authentication state (for any errors during signup)
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val signupState by authViewModel.signupState.collectAsStateWithLifecycle()

    // Handle authentication state changes
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                isLoading = false
                // Handle signup error
            }

            else -> {}
        }
    }

    // Handle signup state changes
    LaunchedEffect(signupState) {
        when (signupState) {
            is AuthState.Success -> {
                isLoading = false
                onSignUpSuccess()
                authViewModel.resetSignupState()
            }

            is AuthState.Error -> {
                isLoading = false
                // Show error message - you can set specific field errors here
                passwordError = (signupState as AuthState.Error).message
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

    val annotatedText = buildAnnotatedString {
        append("I agree to UniCharge ")
        withStyle(style = SpanStyle(color = colors.primary)) {
            append("Public Agreement, Terms, Privacy Policy")
        }
        append(", and confirm that I am over 17 years old.")
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
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
                text = "Please fill in your details to Create your Account.",
                fontSize = 16.sp,
                color = colors.onBackground.copy(alpha = 0.7f),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name Field
            Text(
                text = "Full Name",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    fullNameError = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "John Doe",
                        color = colors.onBackground.copy(alpha = 0.5f)
                    )
                },
                isError = fullNameError.isNotEmpty(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colors.surface,
                    focusedContainerColor = colors.surface,
                    unfocusedIndicatorColor = colors.outline.copy(alpha = 0.5f),
                    focusedIndicatorColor = colors.primary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                )
            )

            if (fullNameError.isNotEmpty()) {
                Text(
                    text = fullNameError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Field
            Text(
                text = "Phone Number",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "+91 **********",
                        color = colors.onBackground.copy(alpha = 0.5f)
                    )
                },
                isError = phoneError.isNotEmpty(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colors.surface,
                    focusedContainerColor = colors.surface,
                    unfocusedIndicatorColor = colors.outline.copy(alpha = 0.5f),
                    focusedIndicatorColor = colors.primary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                )
            )

            if (phoneError.isNotEmpty()) {
                Text(
                    text = phoneError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

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
                        "Enter password",
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

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field
            Text(
                text = "Confirm Password",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = ""
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Re-enter password",
                        color = colors.onBackground.copy(alpha = 0.5f)
                    )
                },
                isError = confirmPasswordError.isNotEmpty(),
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
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

            if (confirmPasswordError.isNotEmpty()) {
                Text(
                    text = confirmPasswordError,
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

            Spacer(modifier = Modifier.height(16.dp))

            // Sign in link
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 14.sp,
                    color = colors.onBackground.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Sign in",
                    fontSize = 14.sp,
                    color = colors.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(
                        onClick = onSignInClick,
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
                    if (validateSignUpForm(
                            fullName,
                            email,
                            phone,
                            password,
                            confirmPassword,
                            isChecked
                        )
                    ) {
                        // Call actual API registration
                        authViewModel.register(fullName, email, phone, password)
                    } else {
                        // Show validation errors
                        if (fullName.isEmpty()) fullNameError = "Full name is required"
                        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches()
                        ) {
                            emailError = "Please enter a valid email"
                        }
                        if (phone.isEmpty()) phoneError = "Phone number is required"
                        if (password.isEmpty() || password.length < 6) {
                            passwordError = "Password must be at least 6 characters"
                        }
                        if (confirmPassword != password) {
                            confirmPasswordError = "Passwords do not match"
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
        }
    }
}

private fun validateSignUpForm(
    fullName: String,
    email: String,
    phone: String,
    password: String,
    confirmPassword: String,
    isChecked: Boolean
): Boolean {
    var isValid = true

    if (fullName.isEmpty()) {
        // Set full name error
        isValid = false
    }

    if (email.isEmpty()) {
        // Set email error
        isValid = false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        // Set email format error
        isValid = false
    }

    if (phone.isEmpty()) {
        // Set phone error
        isValid = false
    }

    if (password.isEmpty()) {
        // Set password error
        isValid = false
    } else if (password.length < 6) {
        // Set password length error
        isValid = false
    }

    if (confirmPassword.isEmpty()) {
        // Set confirm password error
        isValid = false
    } else if (password != confirmPassword) {
        // Set password mismatch error
        isValid = false
    }

    if (!isChecked) {
        // Show agreement error
        isValid = false
    }

    return isValid
}