package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StationDetailScreen(
    name: String,
    address: String,
    rating: Double,
    reviews: Int,
    status: String,
    distanceKm: Double,
    timeMins: Int,
    onViewClick: () -> Unit,
    onBookClick: () -> Unit,
    onArrowClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val isAvailable = status.equals("Available", ignoreCase = true)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ---------- TOP: NAME + DIAGONAL GREEN ARROW ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        name,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onSurface
                    )
                    Text(
                        address,
                        color = colors.onSurface.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }

                // Green diagonal navigation button (from screenshot)
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF34C759), CircleShape)
                        .clickable { onArrowClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(22.dp)
                            .rotate(-20f)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // ---------- RATING ----------
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = rating.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

                Spacer(Modifier.width(4.dp))

                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < rating.toInt()) Color(0xFFFFA726) else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    "($reviews reviews)",
                    color = colors.onSurface.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // ---------- STATUS + DISTANCE + TIME ----------
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Status Chip (green or red)
                Surface(
                    color = if (isAvailable) Color(0xFF18C77B) else Color(0xFFE74C3C),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        status,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.width(12.dp))

                Icon(
                    Icons.Default.Place, contentDescription = null,
                    tint = Color.Gray, modifier = Modifier.size(18.dp)
                )
                Text("${distanceKm} km", modifier = Modifier.padding(start = 4.dp))

                Spacer(Modifier.width(10.dp))

                Icon(
                    Icons.Default.DirectionsCar, contentDescription = null,
                    tint = Color.Gray, modifier = Modifier.size(18.dp)
                )
                Text("${timeMins} mins", modifier = Modifier.padding(start = 4.dp))
            }

            Spacer(Modifier.height(20.dp))

            // ---------- BUTTONS ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // View Button (Outlined)
                OutlinedButton(
                    onClick = onViewClick,
                    shape = RoundedCornerShape(22.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View", color = colors.primary)
                }

                Spacer(Modifier.width(16.dp))

                // Book Button (Filled)
                Button(
                    onClick = onBookClick,
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Book", color = Color.White)
                }
            }
        }
    }
}

