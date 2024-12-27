package org.flyhigh.os.RepresentativePanel.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.core.Icon
import org.flyhigh.os.Models.FlightData


@Composable
fun RepresentativeHomeView(vm: RepresentativeHomeViewModel, navController: NavController) {

    val flights by vm.flights

    MaterialTheme {

        LaunchedEffect(Unit) {
            vm.getFlightsForRepresentative()
        }


        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF2FF)) // Light blue background
        ) {
            // Left Menu Bar
            LeftMenuBar(navController)

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                // Header Section
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Statistics Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatCard(title = "Total Flights", value = flights.size.toString(), growth = "+10.0%")
                    StatCard(title = "Upcoming Flights", value = flights.filter { it.state == "scheduled" }.size.toString(), growth = "+12.0%")
                }

                // Flights List
                Text(
                    text = "Upcoming Flights",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn {
                    items(vm.flights.value) { flight ->
                        FlightItem(
                            flight = flight,
                            onDeleteClick = { /* Add delete functionality */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LeftMenuBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(200.dp)
            .background(Color(0xFF2E3B4E)) // Dark grayish-blue color
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // User Profile
        Row(
            modifier = Modifier.padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Maria",
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "maria@inha.uz",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.caption
                )
            }
        }

        // Menu Items
        MenuItem(icon = Icons.Default.Menu, label = "Dashboard", onClick = {

        })
        MenuItem(icon = Icons.Default.Home, label = "Company", onClick = {
            navController.navigate(route = "company")
        })
        MenuItem(icon = Icons.Default.Info, label = "Add Flights", onClick = {
            navController.navigate(route = "addFlight")
        })
        MenuItem(icon = Icons.Default.ExitToApp, label = "Exit", onClick = {
            navController.navigate("auth")
        })
    }
}

@Composable
fun MenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable {
                            onClick()
                       },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun StatCard(title: String, value: String, growth: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.caption)
        Text(
            text = value,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = growth,
            color = Color.Green,
            style = MaterialTheme.typography.caption
        )
    }
}


@Composable
fun FlightItem(flight: FlightData, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Date Column
        Column(
            modifier = Modifier
                .width(80.dp)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Example day formatting (Monday)
            Text(
                text = flight.startDate.substring(0, 3), // First 3 letters of the day
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2E3B4E)
            )
            // Date number
            Text(
                text = flight.startDate.substring(8, 10), // Extract the date part
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2E3B4E)
            )
        }

        // Flight Details Column
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "${flight.fromAirport} / ${flight.toAirport}",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            Text(
                text = "from ${flight.startDate} to ${flight.endDate}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }

        // Seats Column
        Text(
            text = "${flight.baggagePerPerson} seats",
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color(0xFF2E3B4E)
        )

        // Flight Number Column
        Text(
            text = flight.flightId,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )

        // Plane Model Column
        Text(
            text = "Boeing ${flight.planeId}",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )

        Text(
            text = "${flight.state}",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )

        // Delete Button Column
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFEDED)),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .size(40.dp)
                .padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}


