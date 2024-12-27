package org.flyhigh.os.RepresentativePanel.Main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes2
import org.flyhigh.os.Components.BackButton
import org.flyhigh.os.Models.FlightData


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddFlightsView(vm: RepresentativeHomeViewModel, navController: NavController) {

    val flightId = remember { mutableStateOf("") }
    val airlineId = remember { mutableStateOf("") }
    val planeId = remember { mutableStateOf("") }
    val includesFood = remember { mutableStateOf(false) }
    val fromAirport = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val startDateTimeZone = remember { mutableStateOf(0) }
    val toAirport = remember { mutableStateOf("") }
    val isInternationalFlight = remember { mutableStateOf(false) }
    val endDate = remember { mutableStateOf("") }
    val endDateTimeZone = remember { mutableStateOf(0) }
    val refund = remember { mutableStateOf(0.80) }
    val baggagePerPerson = remember { mutableStateOf(0) }
    val carryOnBaggagePerPerson = remember { mutableStateOf(0) }
    val isExchangeableForAdditionalFee = remember { mutableStateOf(false) }
    val state = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            DefaultRowView(modifier = Modifier.fillMaxWidth()) {
                Text("Add Flight", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = flightId.value,
                    title = "Flight ID",
                    action = { flightId.value = it }
                )

                TextFieldBoxes2(
                    text = airlineId.value,
                    title = "Airline ID",
                    action = { airlineId.value = it }
                )
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = planeId.value,
                    title = "Plane ID",
                    action = { planeId.value = it }
                )

                TextFieldBoxes2(
                    text = fromAirport.value,
                    title = "From Airport",
                    action = { fromAirport.value = it }
                )
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = toAirport.value,
                    title = "To Airport",
                    action = { toAirport.value = it }
                )

                TextFieldBoxes2(
                    text = startDate.value,
                    title = "Start Date (ISO 8601)",
                    action = { startDate.value = it }
                )
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = endDate.value,
                    title = "End Date (ISO 8601)",
                    action = { endDate.value = it }
                )

                TextFieldBoxes2(
                    text = state.value,
                    title = "State",
                    action = { state.value = it }
                )
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = baggagePerPerson.value.toString(),
                    title = "Baggage per Person (kg)",
                    action = { baggagePerPerson.value = it.toIntOrNull() ?: 0 }
                )

                TextFieldBoxes2(
                    text = carryOnBaggagePerPerson.value.toString(),
                    title = "Carry-On Baggage per Person (kg)",
                    action = { carryOnBaggagePerPerson.value = it.toIntOrNull() ?: 0 }
                )
            }

            DefaultRowView {
                TextFieldBoxes2(
                    text = refund.value.toString(),
                    title = "Refund Rate",
                    action = { refund.value = it.toDoubleOrNull() ?: 0.80 }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val flightData = FlightData(
                        flightId = flightId.value,
                        airlineId = airlineId.value,
                        planeId = planeId.value,
                        includesFood = includesFood.value,
                        fromAirport = fromAirport.value,
                        startDate = startDate.value,
                        startDateTimeZone = startDateTimeZone.value,
                        toAirport = toAirport.value,
                        isInternationalFlight = isInternationalFlight.value,
                        endDate = endDate.value,
                        endDateTimeZone = endDateTimeZone.value,
                        refund = refund.value,
                        baggagePerPerson = baggagePerPerson.value,
                        carryOnBaggagePerPerson = carryOnBaggagePerPerson.value,
                        isExchangeableForAdditionalFee = isExchangeableForAdditionalFee.value,
                        state = state.value
                    )
                    // TODO: Save flightData or pass to ViewModel
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }

        BackButton(navController = navController)
    }
}
