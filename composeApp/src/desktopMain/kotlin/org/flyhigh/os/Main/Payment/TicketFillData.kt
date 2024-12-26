package org.flyhigh.os.Main.Payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.flyhigh.os.Main.FlightsView.SelectedSeats
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.Models.User


@Composable
fun TicketFillData(
    flightCombination: FlightCombination,
    homeViewModel: HomeViewModel,
    selectedSeats: List<SelectedSeats>,
    navController: NavController
) {
    val adultNumber by homeViewModel.adultNumber.collectAsState()
    val childrenNumber by homeViewModel.childrenNumber.collectAsState()
    val infantsNumber by homeViewModel.infantsNumber.collectAsState()

    val totalPassengers = adultNumber + childrenNumber + infantsNumber
    val passengersData = remember { mutableStateListOf<User>() }

    // Initialize with empty User objects for all passengers
    LaunchedEffect(totalPassengers) {
        if (passengersData.size != totalPassengers) {
            passengersData.clear()
            repeat(totalPassengers) {
                passengersData.add(
                    User(
                        userId = "",
                        email = "",
                        password = "",
                        lastName = "",
                        firstName = "",
                        phoneNumber = "",
                        birthdate = "",
                        passport = "",
                        foreignPassport = null,
                        citizenship = "",
                        addressCountry = "",
                        addressCity = "",
                        addressState = "",
                        addressLine1 = "",
                        addressLine2 = null,
                        userState = "" // This is only for internal application state
                    )
                )
            }
        }
    }

//    var isFormValid by remember { mutableStateOf(false) }

    // Validate form whenever passenger data changes
    val isFormValid by derivedStateOf {
        passengersData.all { passenger ->
            println("data changed")
            passenger.lastName.isNotBlank() &&
                    passenger.firstName.isNotBlank() &&
                    passenger.phoneNumber.isNotBlank() &&
                    passenger.birthdate.isNotBlank() &&
                    passenger.passport.isNotBlank() &&
                    passenger.citizenship.isNotBlank() &&
                    passenger.addressCountry.isNotBlank() &&
                    passenger.addressCity.isNotBlank() &&
                    passenger.addressState.isNotBlank() &&
                    passenger.addressLine1.isNotBlank()
        }
    }


    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(passengersData.size) { index ->
                    val passenger = passengersData[index]
                    PassengerForm(
                        passenger = passenger,
                        onPassengerChange = { updatedPassenger ->
                            passengersData[index] = updatedPassenger
                        },
                        index = index + 1,
                        isAdult = index < adultNumber
                    )
                }
            }

            Button(
                onClick = {
                    val flightCombinationJson = Json.encodeToString(flightCombination)
                    val selectedSeatsStr = Json.encodeToString(selectedSeats)
                    navController.navigate("payment/$flightCombinationJson/$selectedSeatsStr")
                },
                enabled = isFormValid,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun PassengerForm(
    passenger: User,
    onPassengerChange: (User) -> Unit,
    index: Int,
    isAdult: Boolean
) {
    val label = if (isAdult) "Ticket $index for adults" else "Ticket $index for children"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = passenger.lastName,
                onValueChange = { onPassengerChange(passenger.copy(lastName = it)) },
                label = { Text("Last Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = passenger.firstName,
                onValueChange = { onPassengerChange(passenger.copy(firstName = it)) },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = passenger.birthdate,
                onValueChange = { onPassengerChange(passenger.copy(birthdate = it)) },
                label = { Text("Date of birth") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = passenger.citizenship,
                onValueChange = { onPassengerChange(passenger.copy(citizenship = it)) },
                label = { Text("Citizenship") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = passenger.phoneNumber,
            onValueChange = { onPassengerChange(passenger.copy(phoneNumber = it)) },
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = passenger.passport,
            onValueChange = { onPassengerChange(passenger.copy(passport = it)) },
            label = { Text("Passport ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Address",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = passenger.addressCountry,
                onValueChange = { onPassengerChange(passenger.copy(addressCountry = it)) },
                label = { Text("Country") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = passenger.addressCity,
                onValueChange = { onPassengerChange(passenger.copy(addressCity = it)) },
                label = { Text("City") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = passenger.addressState,
                onValueChange = { onPassengerChange(passenger.copy(addressState = it)) },
                label = { Text("State") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = passenger.addressLine1,
            onValueChange = { onPassengerChange(passenger.copy(addressLine1 = it)) },
            label = { Text("Address line 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = passenger.addressLine2 ?: "",
            onValueChange = { onPassengerChange(passenger.copy(addressLine2 = it)) },
            label = { Text("Address line 2") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
