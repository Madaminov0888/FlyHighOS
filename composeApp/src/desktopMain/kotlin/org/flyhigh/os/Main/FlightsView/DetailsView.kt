package org.flyhigh.os.Main.FlightsView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.Components.BackButton
import org.flyhigh.os.Components.PrimaryButton
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.Models.FlightData

@Composable
fun DetailsView(flightCombination: FlightCombination, navController: NavController, vm: DetailsViewModel) {
    MaterialTheme {
        flightCombination.returnFlights?.let { returnFlights ->
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Header Text
                    item {
                        DefaultRowView {
                            Text(
                                text = "Flight Details",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 25.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                    this.items(flightCombination.flights) { flight ->
                        DetailsFlightInfoView(flight = flight, navController = navController, vm = vm)
                    }

                    items(returnFlights) { flight ->
                        DetailsFlightInfoView(flight = flight, navController = navController, vm = vm)
                    }

                    item {
                        PrimaryButton(
                            text = "Continue",
                            backgroundColor = Color.Black,
                            textColor = Color.White,
                            disabled = false,
                            onClick = {
                                val flightJson = Json.encodeToString(flightCombination)
                                navController.navigate(route = "seatPlaces/$flightJson")
                            }
                        )
                    }
                }

                BackButton(navController)
            }
        }
    }
}
















@Composable
fun DetailsFlightInfoView(flight: FlightData, navController: NavController, vm: DetailsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Flight Route
        Text(
            text = "${flight.fromAirport} → ${flight.toAirport}",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Flight Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Airline and Flight Number
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Blue, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Uzbekistan Airways", // Replace with real airline name if available
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Text(
                        text = "Flight No. ${flight.flightId}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Departure and Arrival Info
                Column {
                    FlightTimeInfo(
                        time = flight.startDate,
                        location = flight.fromAirport
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlightTimeInfo(
                        time = flight.endDate,
                        location = flight.toAirport
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // New Flight Details (Baggage, Carry-on, Exchangeability, Refunds)
                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                FlightAdditionalInfo(
                    title = "Baggage",
                    value = "${flight.baggagePerPerson} × ${flight.baggagePerPerson}kg",
                    isAvailable = flight.baggagePerPerson > 0
                )
                FlightAdditionalInfo(
                    title = "Carry-on",
                    value = "${flight.carryOnBaggagePerPerson} × ${flight.carryOnBaggagePerPerson}kg",
                    isAvailable = flight.carryOnBaggagePerPerson > 0
                )
                FlightAdditionalInfo(
                    title = "Exchangeable for additional fee",
                    value = if (flight.isExchangeableForAdditionalFee) "Available" else "Not Available",
                    isAvailable = flight.isExchangeableForAdditionalFee
                )
                FlightAdditionalInfo(
                    title = "Refunds",
                    value = "${(flight.refund * 100).toInt()}%",
                    isAvailable = flight.refund > 0.0
                )
                FlightAdditionalInfo(
                    title = "Includes Food",
                    value = if (flight.includesFood) "Available" else "Not Available",
                    isAvailable = flight.includesFood
                )
            }
        }
    }
}

@Composable
fun FlightAdditionalInfo(title: String, value: String, isAvailable: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status Indicator
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(if (isAvailable) Color.Green else Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Title and Value
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun FlightTimeInfo(time: String, location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = location,
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}
