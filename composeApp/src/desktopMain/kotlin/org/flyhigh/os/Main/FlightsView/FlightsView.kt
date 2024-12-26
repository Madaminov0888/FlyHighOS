package org.flyhigh.os.Main.FlightsView

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.flyhigh.os.Components.BackButton
import org.flyhigh.os.Components.CSColors
import org.flyhigh.os.Main.Extensions
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Main.SearchFieldView
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.Models.FlightData

@Composable
fun FlightsView(navController: NavController, vm: HomeViewModel) {
    LaunchedEffect(Unit) {
        vm.getFlightResponse()
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                SearchFieldView(vm, navController)

                Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    FilterView(vm)

                    FlightsListView(vm, navController)
                }
            }


            BackButton(navController)
        }
    }
}



@Composable
fun FlightsListView(vm: HomeViewModel, navController: NavController) {

    val flightCombinations by vm.listOfFlightResponse.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(25.dp)
            .fillMaxWidth()
        , horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            Text("Results", fontSize = 30.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp))
        }

        item {
            for (flightCombination in flightCombinations) {
                FlightCombinationView(flightCombination, vm, navController)
            }
        }
    }
}



@Composable
fun FilterView(vm: HomeViewModel) {
    val directChosen by vm.direct.collectAsState()

    Column(
        modifier =
        Modifier
            .padding(5.dp)
            .fillMaxWidth(0.2f)
            .fillMaxHeight()
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
            .padding(10.dp)
        ,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Filters", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Direct Flight", fontWeight = FontWeight.Normal, fontSize = 17.sp)

            Button(
                onClick = { vm.updateDirect(!directChosen) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (directChosen) CSColors.skyBlue else Color.White
                ),
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlightCombinationView(
    flightCombination: FlightCombination,
    vm: HomeViewModel,
    navController: NavController,
) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .onClick {

            }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Text(
                text = vm.getAirlineNameFromID(flightCombination.flightCombinationId),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            // Flights timeline
            FlightTimelineView(flights = flightCombination.flights)

            // Show Return Flights only if not empty
            flightCombination.returnFlights?.takeIf { it.isNotEmpty() }?.let { returnFlights ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Return Flight",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                FlightTimelineView(flights = returnFlights)
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                "$" + flightCombination.flightsOverallPrice.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                "See Details >>",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    val flightJson = Json.encodeToString(flightCombination)
                    navController.navigate(route = "detailsView/$flightJson")
                }
            )
        }
    }
}

@Composable
fun FlightTimelineView(
    flights: List<FlightData>
) {
    val departureFlight = flights.first()
    val arrivalFlight = flights.last()
    val ex = Extensions()

    val departureTime = ex.formatTime(departureFlight.startDate)
    val arrivalTime = ex.formatTime(arrivalFlight.endDate)
    val departureDate = ex.formatDate(departureFlight.startDate)
    val arrivalDate = ex.formatDate(arrivalFlight.endDate)

    val timeZone = "GMT ${departureFlight.startDateTimeZone}"
    val cities = flights.map { it.fromAirport } + arrivalFlight.toAirport  // Include layover points
    val durations = ex.calculateSegmentDurations(flights)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Departure Column (Time + Date)
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = departureTime,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = departureDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Timeline + Durations
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Segment durations above the timeline
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    durations.forEach { duration ->
                        Text(
                            text = duration,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                // Draw the timeline
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val startX = 0f
                    val endX = size.width
                    val centerY = size.height / 2
                    drawLine(
                        color = Color.Black,
                        start = Offset(startX, centerY),
                        end = Offset(endX, centerY),
                        strokeWidth = 4f
                    )

                    val dotRadius = 8f
                    val spacing = endX / (cities.size - 1)
                    cities.forEachIndexed { index, _ ->
                        val x = spacing * index
                        drawCircle(
                            color = if (index == 1 && flights.size > 1) Color.Red else Color.Black,
                            radius = dotRadius,
                            center = Offset(x, centerY)
                        )
                    }
                }

                // City labels below the timeline
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    cities.forEach { city ->
                        Text(
                            text = city,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Arrival Column (Time + Date)
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = arrivalTime,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = arrivalDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

