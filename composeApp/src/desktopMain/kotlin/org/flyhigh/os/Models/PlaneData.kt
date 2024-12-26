package org.flyhigh.os.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class PlaneDataModel(
    @SerialName("plane_id") val planeId: String,
    @SerialName("airline_id") val airlineId: String,
    @SerialName("plane_type") val planeType: String, // e.g., "Boeing"
    @SerialName("max_floors") val maxFloors: Int
)


@Serializable
data class Seat(
    @SerialName("plane_id") val planeId: String,
    @SerialName("seat_id") val seatId: Int,
    @SerialName("seat_row") val seatRow: Int,
    @SerialName("seat_column") val seatColumn: String, // Allowed values: A-D, A-F, or A-
    @SerialName("seat_class") val seatClass: String,
    @SerialName("floor") val floor: Int
)


@Serializable
data class ReservedSeats(
    @SerialName("purchased_flight_id") val purchasedFlightId: String,
    @SerialName("flight_id") val flightId: String,
    @SerialName("plane_id") val planeId: String,
    @SerialName("seat_id") val seatId: Int
)