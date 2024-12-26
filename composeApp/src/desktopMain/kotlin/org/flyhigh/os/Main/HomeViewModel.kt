package org.flyhigh.os.Main


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.Models.FlightData
import org.flyhigh.os.Models.FlightResponse
import org.flyhigh.os.Models.FlightResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel {

    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Default + viewModelJob)

    private val _selectedFlightCombination = MutableStateFlow<FlightCombination?>(null)
    val selectedFlightCombination: StateFlow<FlightCombination?> get() = _selectedFlightCombination

    fun setFlightCombination(flightCombination: FlightCombination?) {
        _selectedFlightCombination.value = flightCombination
    }


    //TODO TEXTFIELDS

    private val _fromCity = MutableStateFlow("")
    val fromCity: StateFlow<String> get() = _fromCity

    private val _toCity = MutableStateFlow("")
    val toCity: StateFlow<String> get() = _toCity

    private val _flightDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val flightDate: StateFlow<String> get() = _flightDate

    private val _returnDate = MutableStateFlow("")
    val returnDate: StateFlow<String> get() = _returnDate

    private val _adultNumber = MutableStateFlow(1)
    val adultNumber: StateFlow<Int> get() = _adultNumber

    private val _childrenNumber = MutableStateFlow(0)
    val childrenNumber: StateFlow<Int> get() = _childrenNumber

    private val _infantsNumber = MutableStateFlow(0)
    val infantsNumber: StateFlow<Int> get() = _infantsNumber

    private val _flightClass = MutableStateFlow("Economy")
    val flightClass: StateFlow<String> get() =  _flightClass


    val flightClasses: Array<String> = arrayOf("Economy", "Comfort", "Business", "First")


    //TODO Flight Results
    private val _direct = MutableStateFlow(false)
    val direct: StateFlow<Boolean> get() =  _direct


    private val _listOfFlightResponse = MutableStateFlow<List<FlightCombination>>(emptyList())
    val listOfFlightResponse: StateFlow<List<FlightCombination>> get() = _listOfFlightResponse

    private val _airlinesMap = MutableStateFlow<Map<String, String>>(emptyMap())
    val airlinesMap: StateFlow<Map<String, String>> = _airlinesMap


    fun getFlightResponse() {
        val response = FlightResponse(
            status = "OK",
            result = FlightResult(
                listOf(FlightCombination(
                    flightCombinationId = "233",
                    flightsRequestId = "111",
                    flightsOverallPrice = 3900.0,
                    flights =listOf(
                        FlightData(
                            flightId = "FL12345",
                            airlineId = "AIR67890",
                            planeId = "PL123",
                            includesFood = true,
                            fromAirport = "TAS", // Tashkent airport code
                            startDate = "2024-12-25 07:40", // ISO 8601 format
                            startDateTimeZone = 5, // UTC+5
                            toAirport = "IST", // Istanbul airport code
                            isInternationalFlight = true,
                            endDate = "2024-12-25 14:15", // ISO 8601 format
                            endDateTimeZone = 3, // UTC+3
                            refund = 0.80, // 80% refund
                            baggagePerPerson = 20, // 20 kg
                            carryOnBaggagePerPerson = 7, // 7 kg
                            isExchangeableForAdditionalFee = true,
                            state = "Scheduled"
                        ),
                        FlightData(
                            flightId = "FL12346",
                            airlineId = "AIR67890",
                            planeId = "PL126",
                            includesFood = true,
                            fromAirport = "IST", // Istanbul airport code
                            startDate = "2024-12-25 15:40", // ISO 8601 format
                            startDateTimeZone = 3, // UTC+3
                            toAirport = "JFK", // JFK airport code
                            isInternationalFlight = true,
                            endDate = "2024-12-25 21:15", // ISO 8601 format
                            endDateTimeZone = -5, // UTC-5
                            refund = 0.75, // 75% refund
                            baggagePerPerson = 25, // 25 kg
                            carryOnBaggagePerPerson = 10, // 10 kg
                            isExchangeableForAdditionalFee = false,
                            state = "Scheduled"
                        )
                    ),
                    returnFlights = listOf(
                        FlightData(
                            flightId = "FL12347",
                            airlineId = "AIR67890",
                            planeId = "PL123",
                            includesFood = true,
                            fromAirport = "JFK", // JFK airport code
                            startDate = "2024-12-26 09:40", // ISO 8601 format
                            startDateTimeZone = -5, // UTC-5
                            toAirport = "TAS", // Tashkent airport code
                            isInternationalFlight = true,
                            endDate = "2024-12-26 21:15", // ISO 8601 format
                            endDateTimeZone = 5, // UTC+5
                            refund = 0.90, // 90% refund
                            baggagePerPerson = 30, // 30 kg
                            carryOnBaggagePerPerson = 7, // 7 kg
                            isExchangeableForAdditionalFee = true,
                            state = "Scheduled"
                        )
                    )
                ))
            )
        )

        _listOfFlightResponse.value = response.result.flightCombinations

    }

    fun updateDirect(bl: Boolean) { _direct.value = bl }

    //Functions
    fun updateToCity(text: String) { _toCity.value = text }
    fun updateFromCity(text: String) { _fromCity.value = text}
    fun updateFlightDate(date: String) { _flightDate.value = date }
    fun updateReturnDate(date: String) { _returnDate.value = date }
    fun updateChildrenNumber(num: Int) { _childrenNumber.value = num }
    fun updateAdultNumber(num: Int) { _adultNumber.value = num }
    fun updateInfantsNumber(num: Int) { _infantsNumber.value = num }
    fun updateFlightClass(text: String) { _flightClass.value = text}

    fun getPassengersNumber():Int {
        return _adultNumber.value + _childrenNumber.value + _infantsNumber.value
    }

    fun getAirlinesSTR(flightCombination: FlightCombination): String {
        var airlineNames: String = ""
        flightCombination.flights.forEach { flight ->
            val name = getAirlineNameFromID(flight.airlineId)
            airlineNames += "$name, "
        }
        return airlineNames
    }

    fun getAirlineNameFromID(id: String): String {
        return "Uzbekistan Airways"
    }

}