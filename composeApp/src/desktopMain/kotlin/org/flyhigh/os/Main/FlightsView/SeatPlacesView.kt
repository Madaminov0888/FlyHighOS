package org.flyhigh.os.Main.FlightsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.flyhigh.os.Components.PrimaryButton
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.Models.FlightData
import org.flyhigh.os.Models.ReservedSeats
import org.flyhigh.os.Models.Seat

@Composable
fun SeatPlacesView(
    seatsVM: SeatPlacesViewModel,
    homeVM: HomeViewModel,
    navController: NavController,
    flightCombination: FlightCombination,
) {
    val adultNumber by homeVM.adultNumber.collectAsState()
    val childrenNumber by homeVM.childrenNumber.collectAsState()
    val maxSeats = adultNumber + childrenNumber

    LaunchedEffect(Unit) {
        seatsVM.getSeats(planeId = flightCombination.flights[0].planeId)
    }

    MaterialTheme {
        val seatsMap by seatsVM.seatsMap.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.LightGray, Color.White))),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Text(
                    text = "Choose Flight Seats",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    color = Color.DarkGray
                )
            }

            item {
                flightCombination.flights.forEach { flight ->
                    SeatSelectionScreen(
                        viewModel = seatsVM,
                        flight = flight,
                        maxSeats = maxSeats,
                        flightNum = flightCombination.flights.size + (flightCombination.returnFlights?.size ?: 0),
                        homeVM
                    )
                }
            }

            item {
                flightCombination.returnFlights?.forEach { flight ->
                    SeatSelectionScreen(
                        viewModel = seatsVM,
                        flight = flight,
                        maxSeats = maxSeats,
                        flightNum = flightCombination.flights.size + (flightCombination.returnFlights.size ?: 0),
                        homeVM
                    )
                }
            }

            item {
                PrimaryButton(
                    text = "Continue",
                    backgroundColor = Color.Black,
                    textColor = Color.White,
                    onClick = {
                        val flightCombinationJson = Json.encodeToString(flightCombination)
                        val seats = Json.encodeToString(seatsVM.selectedSeats.value)
                        navController.navigate(route = "ticketFillData/$flightCombinationJson/$seats")
                    }
                )
            }
        }
    }
}


@Composable
fun FloorToggle(selectedFloor: MutableState<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { selectedFloor.value = 1 },
            modifier = Modifier.padding(end = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selectedFloor.value == 1) Color(0xFF76C7C0) else Color.LightGray
            )
        ) {
            Text("Floor 1", color = if (selectedFloor.value == 1) Color.White else Color.Black)
        }

        Button(
            onClick = { selectedFloor.value = 2 },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selectedFloor.value == 2) Color(0xFF76C7C0) else Color.LightGray
            )
        ) {
            Text("Floor 2", color = if (selectedFloor.value == 2) Color.White else Color.Black)
        }
    }
}



@Composable
fun SeatSelectionScreen(viewModel: SeatPlacesViewModel, flight: FlightData, maxSeats: Int, flightNum: Int,homeVM: HomeViewModel) {
    val seatsMap by viewModel.seatsMap.collectAsState()
    val purchasedSeats by viewModel.purchasedSeats.collectAsState()
    val selectedFloor = remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        viewModel.getSeats(flight.planeId)
        viewModel.getPurchasedFlights(flightId = flight.flightId)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                "Choose seats for flight No. ${flight.flightId} - Floor ${selectedFloor.value}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp),
                color = Color.Black
            )
            FloorToggle(selectedFloor)

            PassengersLabel("Adults: ${homeVM.adultNumber.value}   Children: ${homeVM.childrenNumber.value}")

            Spacer(modifier = Modifier.height(16.dp))

            SeatClassLabel("Business Class")
            SeatGrid(
                seatsMap = seatsMap,
                purchasedSeats = purchasedSeats,
                seatClass = "Business",
                flight = flight,
                floor = selectedFloor,
                maxSeats = maxSeats,
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(16.dp))

            SeatClassLabel("Economy Class")
            SeatGrid(
                seatsMap = seatsMap,
                purchasedSeats = purchasedSeats,
                seatClass = "Economy",
                flight = flight,
                floor = selectedFloor,
                maxSeats = maxSeats,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun SeatClassLabel(label: String) {
    Text(
        label,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Color.DarkGray
    )
}


@Composable
fun PassengersLabel(label: String) {
    Text(
        label,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Color.DarkGray,
    )
}



@Composable
fun SeatGrid(
    seatsMap: Map<String, Seat>,
    purchasedSeats: List<ReservedSeats>,
    seatClass: String,
    flight: FlightData,
    floor: MutableState<Int>,
    maxSeats: Int,
    viewModel: SeatPlacesViewModel,
) {
    val selectedSeats by viewModel.selectedSeats.collectAsState()
    val flightSelectedSeats = selectedSeats.find { it.flightId == flight.flightId }?.seatIds ?: emptySet()

    val seatRows = seatsMap.values
        .filter { it.seatClass == seatClass && it.planeId == flight.planeId && it.floor == floor.value }
        .groupBy { it.seatRow }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        seatRows.forEach { (row, seats) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "$row",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                val maxColumns = seats.maxOfOrNull { it.seatColumn.toIntOrNull() ?: 0 } ?: 6
                val chunkSize = when (maxColumns) {
                    in 1..4 -> 2
                    in 5..6 -> 3
                    in 7..9 -> 3
                    else -> 3
                }

                seats.sortedBy { it.seatColumn }.chunked(chunkSize).forEachIndexed { index, chunk ->
                    chunk.forEach { seat ->
                        val isReserved = purchasedSeats.any { it.seatId == seat.seatId && it.planeId == seat.planeId }
                        val isSelected = flightSelectedSeats.contains(seat.seatId)
                        val seatColor = when {
                            isReserved -> Color.Gray
                            isSelected -> Color.Green
                            else -> Color(0xFF76C7C0)
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(seatColor, shape = RoundedCornerShape(8.dp))
                                .padding(4.dp)
                                .clickable(enabled = !isReserved) {
                                    viewModel.toggleSeatSelection(flight.flightId, seat.seatId, maxSeats)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(seat.seatColumn, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (index < seats.size / chunkSize - 1) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


