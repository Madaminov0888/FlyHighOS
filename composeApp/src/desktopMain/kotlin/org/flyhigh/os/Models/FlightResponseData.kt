package org.flyhigh.os.Models

import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: FlightResult
)

data class FlightResult(
    @SerializedName("flight_combinations") val flightCombinations: List<FlightCombination>
)

data class FlightCombination(
    @SerializedName("flights_request_id") val flightsRequestId: String,
    @SerializedName("flight_combination_id") val flightCombinationId: String,
    @SerializedName("flights_overall_price") val flightsOverallPrice: Double,
    @SerializedName("flights") val flights: List<FlightData>,
    @SerializedName("return_flights") val returnFlights: List<FlightData>? // Nullable if empty
)

data class FlightData(
    @SerializedName("flight_id") val flightId: String,
    @SerializedName("airline_id") val airlineId: String,
    @SerializedName("plane_id") val planeId: String,
    @SerializedName("includes_food") val includesFood: Boolean,
    @SerializedName("from_airport") val fromAirport: String, // Assuming it's airport code
    @SerializedName("start_date") val startDate: String,
    @SerializedName("start_date_time_zone") val startDateTimeZone: Int,
    @SerializedName("to_airport") val toAirport: String, // Assuming it's airport code
    @SerializedName("is_international_flight") val isInternationalFlight: Boolean,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("end_date_time_zone") val endDateTimeZone: Int,
    @SerializedName("state") val state: String,
    @SerializedName("is_baggage") val isBaggage: Boolean
)