package com.example.unichargeandroid.Screens.Auth

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R
import java.util.Calendar

@Composable
fun UnderlineInputField(
    value: String,
    onValueChanged: (String) -> Unit,
    textColor: Color,
    underlineColor: Color,
    hint: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            textStyle = TextStyle(fontSize = 16.sp, color = textColor),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(hint, color = Color.Gray, fontSize = 16.sp)
                    }
                    innerTextField()
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .height(1.dp)
                .background(underlineColor)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen() {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    val genders = listOf("Male", "Female", "Other")
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            dob = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val scrollState = rememberScrollState() // <-- added scroll state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // <-- make scrollable
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(24.dp))
            Text(
                text = "Skip",
                color = Color(0xFF00C853),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Complete your profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Don't worry, only you can see your personal data. No one else will be able to see it",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Profile image
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ob4_light),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
                    .background(Color.White, CircleShape)
                    .padding(3.dp),
                tint = Color(0xFF00C853)
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // FULL NAME
        Text("Full Name", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        UnderlineInputField(fullName, { fullName = it }, Color.Black, Color.LightGray, "Full Name")

        Spacer(modifier = Modifier.height(20.dp))

        // EMAIL
        Text("Email", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        UnderlineInputField(email, { email = it }, Color.Black, Color.LightGray, "Email")

        Spacer(modifier = Modifier.height(20.dp))

        // GENDER
        Text("Gender", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Box {
            UnderlineInputField(gender, {}, Color.Black, Color.LightGray, "Gender")
            Box(modifier = Modifier.matchParentSize().clickable { expanded = true })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                genders.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { gender = it; expanded = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DOB
        Text("Date of Birth", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Box {
            UnderlineInputField(dob, {}, Color.Black, Color.LightGray, "MM/DD/YYYY")
            Box(modifier = Modifier.matchParentSize().clickable { datePicker.show() })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
            shape = RoundedCornerShape(22.dp)
        ) {
            Text("Continue", fontSize = 14.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteProfileScreen() {
    CompleteProfileScreen()
}
