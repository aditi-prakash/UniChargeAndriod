package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.unichargeandroid.R
import com.example.unichargeandroid.Routes

@Composable
fun BottomNavBar(navController: NavController, selectedItem: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clipToBounds()
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomMenuIcon(
                label = "Home",
                icon = painterResource(R.drawable.home),
                contentDesc = "Home",
                route = Routes.HomeScreen,
                selectedItem = selectedItem,
                navController = navController
            )

            BottomMenuIcon(
                label = "Vehicle",
                icon = painterResource(R.drawable.electric_car),
                contentDesc = "Vehicle",
                route = Routes.VehicleScreen,
                selectedItem = selectedItem,
                navController = navController
            )

            BottomMenuIcon(
                label = "Wallet",
                icon = painterResource(R.drawable.wallet),
                contentDesc = "Wallet",
                route = Routes.WalletScreen,
                selectedItem = selectedItem,
                navController = navController
            )

            BottomMenuIcon(
                label = "Account",
                icon = painterResource(R.drawable.account),
                contentDesc = "Account",
                route = Routes.AccountScreen,
                selectedItem = selectedItem,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomMenuIcon(
    label: String,
    icon: Painter,
    contentDesc: String,
    route: String,
    selectedItem: String,
    navController: NavController
) {
    val isSelected = selectedItem == route

    Column(
        modifier = Modifier
            .clickable(
                enabled = !isSelected,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
//                    popUpTo(navController.graph.startDestinationId) {
//                        saveState = true
//                    }
                }
            }
            .width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDesc,
            modifier = Modifier.size(22.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
    }
}