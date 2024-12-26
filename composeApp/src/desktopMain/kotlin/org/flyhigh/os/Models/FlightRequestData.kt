package org.flyhigh.os.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlightRequest(
    @SerialName("request") val request: String, // "user-get-flights"
    @SerialName("authorization") val authorization: String, // TOKEN
    @SerialName("details") val details: FlightDetails
)

@Serializable
data class FlightDetails(
    @SerialName("input_source") val inputSource: String,
    @SerialName("input_destination") val inputDestination: String,
    @SerialName("input_departure_time") val inputDepartureTime: String? = null,
    @SerialName("input_return_time") val inputReturnTime: String? = null,
    @SerialName("input_flight_type") val inputFlightType: String? = null,
    @SerialName("input_flight_details") val inputFlightDetails: String? = null,
    @SerialName("filter_only_direct") val filterOnlyDirect: String? = null
)