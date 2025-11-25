package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R

@Composable
fun HomeBottomNav() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 6.dp
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Home", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF18C77B),
                selectedTextColor = Color(0xFF18C77B),
                indicatorColor = Color(0x3318C77B)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.electric_car),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Vehicle", fontSize = 10.sp) }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Wallet", fontSize = 10.sp) }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Account", fontSize = 10.sp) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    HomeBottomNav()
}
