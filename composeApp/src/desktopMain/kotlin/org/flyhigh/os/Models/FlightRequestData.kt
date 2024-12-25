package org.flyhigh.os.Models

import com.google.gson.annotations.SerializedName

data class FlightRequest(
    @SerializedName("request") val request: String, // "user-get-flights"
    @SerializedName("authorization") val authorization: String, // TOKEN
    @SerializedName("details") val details: FlightDetails
)

data class FlightDetails(
    @SerializedName("input_source") val inputSource: String,
    @SerializedName("input_destination") val inputDestination: String,
    @SerializedName("input_departure_time") val inputDepartureTime: String? = null,
    @SerializedName("input_return_time") val inputReturnTime: String? = null,
    @SerializedName("input_flight_type") val inputFlightType: String? = null,
    @SerializedName("input_flight_details") val inputFlightDetails: String? = null,
    @SerializedName("filter_only_direct") val filterOnlyDirect: String? = null
)