package org.flyhigh.os.Main.FlightsView

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import org.flyhigh.os.Models.ReservedSeats
import org.flyhigh.os.Models.Seat


class SeatPlacesViewModel {

    private val _seatsMap = MutableStateFlow<Map<String, Seat>>(emptyMap())
    val seatsMap: StateFlow<Map<String, Seat>> get() = _seatsMap

    private val _purchasedSeats = MutableStateFlow<List<ReservedSeats>>(emptyList())
    val purchasedSeats: StateFlow<List<ReservedSeats>> get() = _purchasedSeats



    private val _selectedSeats = MutableStateFlow<List<SelectedSeats>>(emptyList())
    val selectedSeats: StateFlow<List<SelectedSeats>> get() = _selectedSeats




    fun toggleSeatSelection(flightId: String, seatId: Int, maxSeats: Int) {
        val currentSelection = _selectedSeats.value.toMutableList()
        val flightSelection = currentSelection.find { it.flightId == flightId }

        val updatedSeatIds = when {
            flightSelection == null -> setOf(seatId) // Add a new flight selection
            flightSelection.seatIds.contains(seatId) -> flightSelection.seatIds - seatId // Unselect the seat
            flightSelection.seatIds.size < maxSeats -> flightSelection.seatIds + seatId // Select the seat
            else -> flightSelection.seatIds // Max seats limit reached, no change
        }

        if (flightSelection == null) {
            currentSelection.add(SelectedSeats(flightId, updatedSeatIds))
        } else {
            currentSelection.remove(flightSelection)
            if (updatedSeatIds.isNotEmpty()) {
                currentSelection.add(flightSelection.copy(seatIds = updatedSeatIds))
            }
        }

        _selectedSeats.value = currentSelection
    }






    fun getSeats(planeId: String) {
        val seats = mutableListOf<Seat>()
        var seatCounter = 1

        val seatConfigurations = mapOf(
            "First" to listOf("A", "B"),
            "Business" to listOf("A", "B", "C", "D"),
            "Economy" to listOf("A", "B", "C", "D", "E", "F", "G", "H", "I")
        )

        val classRows = mapOf(
            "First" to 1..3,
            "Business" to 4..10,
            "Economy" to 11..30
        )

        seatConfigurations.forEach { (seatClass, columns) ->
            classRows[seatClass]?.forEach { row ->
                columns.forEach { column ->
                    seats.add(
                        Seat(
                            planeId = planeId,
                            seatId = seatCounter++,
                            seatRow = row,
                            seatColumn = column,
                            seatClass = seatClass,
                            floor = 1
                        )
                    )
                }
            }
        }

        val seatsMap = seats.associateBy { "${it.planeId}_${it.seatId}" }
        _seatsMap.value = _seatsMap.value + seatsMap
    }


    fun getPurchasedFlights(flightId: String) {
        val purchasedFlights = listOf(
            ReservedSeats(
                purchasedFlightId = "PF001",
                flightId = "FL12347",
                planeId = "PL123",
                seatId = 42
            ),
            ReservedSeats(
                purchasedFlightId = "PF003",
                flightId = "FL12347",
                planeId = "PL123",
                seatId = 48
            ),
            ReservedSeats(
                purchasedFlightId = "PF004",
                flightId = "FL12347",
                planeId = "PL123",
                seatId = 50
            ),
            ReservedSeats(
                purchasedFlightId = "PF005",
                flightId = "FL12347",
                planeId = "PL123",
                seatId = 51
            ),
            ReservedSeats(
                purchasedFlightId = "PF006",
                flightId = "FL12347",
                planeId = "PL123",
                seatId = 52
            )
        )

        _purchasedSeats.value = _purchasedSeats.value + purchasedFlights
    }
}







//
//
//fun getSeatsForPlane(planeId: String): List<Seat> {
//    // Check if the planeId exists in the _seatsMap
//    val allSeats = _seatsMap.value.values.filter { it.planeId == planeId }
//    return allSeats
//}
//
//
//// Function to get purchased seats for a particular flight
//fun getPurchasedSeatsForFlight(flightId: String): List<Seat> {
//    // Assuming purchasedFlights are linked to the seats via seatId
//    val purchasedFlightIds = _purchasedSeats.value.filter { it.flightId == flightId }.map { it.purchasedFlightId }
//
//    // Return seats that are purchased (mocking the association)
//    val purchasedSeats = _seatsMap.value.values.filter { seat ->
//        purchasedFlightIds.contains(seat.seatId)  // Assuming seatId is tied to a purchased flight
//    }
//    return purchasedSeats
//}



@Serializable
data class SelectedSeats(
    val flightId: String,
    val seatIds: Set<Int>
)
