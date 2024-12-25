package org.flyhigh.os.Main


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.flyhigh.os.Managers.NetworkManager
import org.flyhigh.os.Models.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.swing.plaf.nimbus.State

class HomeViewModel {

    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Default + viewModelJob)

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
                    flights = listOf(FlightData(
                        flightId = "FL12345",
                        airlineId = "AIR67890",
                        planeId = "PL123",
                        includesFood = true,
                        fromAirport = "TAS", // Tashkent airport code
                        startDate = "2024-12-25 07:40", // Exact start date and time
                        startDateTimeZone = 5, // UTC+5
                        toAirport = "IST", // JFK airport code
                        isInternationalFlight = true,
                        endDate = "2024-12-25 14:15", // Exact end date and time
                        endDateTimeZone = -5, // UTC-5
                        state = "Scheduled",
                        isBaggage = true
                    )
                        ,FlightData(
                        flightId = "FL12345",
                        airlineId = "AIR67890",
                        planeId = "PL123",
                        includesFood = true,
                        fromAirport = "TAS", // Tashkent airport code
                        startDate = "2024-12-25 15:40", // Exact start date and time
                        startDateTimeZone = 5, // UTC+5
                        toAirport = "JFK", // JFK airport code
                        isInternationalFlight = true,
                        endDate = "2024-12-25 21:15", // Exact end date and time
                        endDateTimeZone = -5, // UTC-5
                        state = "Scheduled",
                        isBaggage = true
                    )),
                    returnFlights = listOf(FlightData(
                        flightId = "FL12345",
                        airlineId = "AIR67890",
                        planeId = "PL123",
                        includesFood = true,
                        fromAirport = "TAS", // Tashkent airport code
                        startDate = "2024-12-26 09:40", // Exact start date and time
                        startDateTimeZone = 5, // UTC+5
                        toAirport = "JFK", // JFK airport code
                        isInternationalFlight = true,
                        endDate = "2024-12-26 21:15", // Exact end date and time
                        endDateTimeZone = -5, // UTC-5
                        state = "Scheduled",
                        isBaggage = true
                    ))
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