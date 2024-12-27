package org.flyhigh.os.AdminPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.flyhigh.os.Models.FlightData
import org.flyhigh.os.Models.RepresentativeData
import org.flyhigh.os.Models.User

@Composable
fun AdminHomeView(vm: AdminHomeViewModel, navController: NavController) {
    var activeView by remember { mutableStateOf("Users") }
    val userList = remember { mutableStateOf(emptyList<User>()) }
    val representativeList = remember { mutableStateOf(emptyList<RepresentativeData>()) }
    val flightList = remember { mutableStateOf(emptyList<FlightData>()) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("ADMIN", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            NavBar2(
                vm = vm,
                navController = navController,
                activeView = activeView,
                onSwitchView = { newView ->
                    activeView = newView
                    when (newView) {
                        "Users" -> vm.fetchUsers { userList.value = it }
                        "Representatives" -> vm.fetchRepresentatives { representativeList.value = it }
                        "Flights" -> vm.fetchFlights { flightList.value = it }
                    }
                }
            )

            when (activeView) {
                "Users" -> UserTable(userList.value)
                "Representatives" -> RepresentativeTable(representativeList.value)
                "Flights" -> FlightTable(flightList.value)
            }
        }
    }
}

@Composable
fun NavBar2(
    vm: AdminHomeViewModel,
    navController: NavController,
    activeView: String,
    onSwitchView: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text(
            "Users",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable { onSwitchView("Users") },
            fontSize = 20.sp,
            fontWeight = if (activeView == "Users") FontWeight.Bold else FontWeight.Medium
        )

        Text(
            "Representatives",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable { onSwitchView("Representatives") },
            fontSize = 20.sp,
            fontWeight = if (activeView == "Representatives") FontWeight.Bold else FontWeight.Medium
        )

        Text(
            "Flights",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable { onSwitchView("Flights") },
            fontSize = 20.sp,
            fontWeight = if (activeView == "Flights") FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
fun TableHeader(text: String, width: Dp) {
    Text(
        text,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
}

@Composable
fun TableCell(text: String, width: Dp) {
    Text(
        text,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 8.dp),
        fontSize = 14.sp
    )
}

@Composable
fun UserTable(users: List<User>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(vertical = 8.dp)
            ) {
                TableHeader("User ID", width = 250.dp)
                TableHeader("Email", width = 250.dp)
                TableHeader("First Name", width = 250.dp)
                TableHeader("Last Name", width = 250.dp)
                TableHeader("State", width = 250.dp)
            }
        }

        items(users) { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TableCell(user.userId, width = 250.dp)
                TableCell(user.email, width = 250.dp)
                TableCell(user.firstName, width = 250.dp)
                TableCell(user.lastName, width = 250.dp)
                TableCell(user.userState, width = 250.dp)
            }
        }
    }
}



@Composable
fun RepresentativeTable(representatives: List<RepresentativeData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray)) {
                TableHeader("Representative ID", width = 250.dp)
                TableHeader("Email", width = 250.dp)
                TableHeader("State", width = 250.dp)
            }
        }
        items(representatives) { rep ->
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(rep.representative_id, width = 250.dp)
                TableCell(rep.email, width = 250.dp)
                TableCell(rep.state, width = 250.dp)
            }
        }
    }
}

@Composable
fun FlightTable(flights: List<FlightData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray)) {
                TableHeader("Flight ID", width = 250.dp)
                TableHeader("From Airport", width = 250.dp)
                TableHeader("To Airport", width = 250.dp)
                TableHeader("Start Date", width = 250.dp)
                TableHeader("End Date", width = 250.dp)
            }
        }
        items(flights) { flight ->
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(flight.flightId, width = 250.dp)
                TableCell(flight.fromAirport, width = 250.dp)
                TableCell(flight.toAirport, width = 250.dp)
                TableCell(flight.startDate, width = 250.dp)
                TableCell(flight.endDate, width = 250.dp)
            }
        }
    }
}
