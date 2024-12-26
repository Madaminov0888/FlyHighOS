package org.flyhigh.os.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FlightResponse(
    @SerialName("status") val status: String,
    @SerialName("result") val result: FlightResult
)

@Serializable
data class FlightResult(
    @SerialName("flight_combinations") val flightCombinations: List<FlightCombination>
)

@Serializable data class FlightCombination(
    @SerialName("flights_request_id") val flightsRequestId: String,
    @SerialName("flight_combination_id") val flightCombinationId: String,
    @SerialName("flights_overall_price") val flightsOverallPrice: Double,
    @SerialName("flights") val flights: List<FlightData>,
    @SerialName("return_flights") val returnFlights: List<FlightData>? // Nullable if empty
)


@Serializable
data class FlightData(
    @SerialName("flight_id") val flightId: String,
    @SerialName("airline_id") val airlineId: String,
    @SerialName("plane_id") val planeId: String,
    @SerialName("includes_food") val includesFood: Boolean,
    @SerialName("from_airport") val fromAirport: String, // Airport code
    @SerialName("start_date") val startDate: String, // Assuming ISO 8601 date string (e.g., "2024-12-25T07:40:00Z")
    @SerialName("start_date_time_zone") val startDateTimeZone: Int,
    @SerialName("to_airport") val toAirport: String, // Airport code
    @SerialName("is_international_flight") val isInternationalFlight: Boolean,
    @SerialName("end_date") val endDate: String, // Assuming ISO 8601 date string
    @SerialName("end_date_time_zone") val endDateTimeZone: Int,
    @SerialName("refund") val refund: Double = 0.80, // Default to 80% refund (0.80)
    @SerialName("baggage_per_person") val baggagePerPerson: Int, // Kilograms
    @SerialName("carry_on_baggage_per_person") val carryOnBaggagePerPerson: Int, // Kilograms
    @SerialName("is_exchangeable_for_additional_fee") val isExchangeableForAdditionalFee: Boolean,
    @SerialName("state") val state: String
)