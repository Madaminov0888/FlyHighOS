package org.flyhigh.os.RepresentativePanel.Main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.flyhigh.os.Models.FlightData


class RepresentativeHomeViewModel {
    // MutableState to hold flights, allows updating asynchronously
    private val _flights = mutableStateOf<List<FlightData>>(emptyList())
    val flights: State<List<FlightData>> get() = _flights

    private val _airline = mutableStateOf<AirlinesDataClass?>(null)
    val airlinesData: State<AirlinesDataClass?> get() = _airline


    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _logo = MutableStateFlow("")
    val logo: StateFlow<String> get() = _logo

    private val _contactNumber = MutableStateFlow("")
    val contactNumber: StateFlow<String> get() = _contactNumber

    private val _contactEmail = MutableStateFlow("")
    val contactEmail: StateFlow<String> get() = _contactEmail

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _tin = MutableStateFlow("")
    val tin: StateFlow<String> get() = _tin

    private val _accountHolderName = MutableStateFlow("")
    val accountHolderName: StateFlow<String> get() = _accountHolderName

    private val _bankName = MutableStateFlow("")
    val bankName: StateFlow<String> get() = _bankName

    private val _bankSwiftCode = MutableStateFlow("")
    val bankSwiftCode: StateFlow<String> get() = _bankSwiftCode

    private val _bankAccountNumber = MutableStateFlow("")
    val bankAccountNumber: StateFlow<String> get() = _bankAccountNumber

    private val _airlineDescription = MutableStateFlow("")
    val airlineDescription: StateFlow<String> get() = _airlineDescription

    fun updateName(text: String) { _name.value = text }
    fun updateLogo(text: String) { _logo.value = text }
    fun updateContactNumber(text: String) { _contactNumber.value = text }
    fun updateContactEmail(text: String) { _contactEmail.value = text }
    fun updateAddress(text: String) { _address.value = text }
    fun updateTIN(text: String) { _tin.value = text }
    fun updateAccountHolderName(text: String) { _accountHolderName.value = text }
    fun updateBankName(text: String) { _bankName.value = text }
    fun updateBankSwiftCode(text: String) { _bankSwiftCode.value = text }
    fun updateBankAccountNumber(text: String) { _bankAccountNumber.value = text }
    fun updateAirlineDescription(text: String) { _airlineDescription.value = text }


    fun getAirlines(){
        val airline = AirlinesDataClass(
            airline_id = "assad",
            representative_id = "sadasd",
            name = "asdsad",
            logo = "asdasd",
            contact_number = "adasd",
            contact_email = "asdasd",
            address = "asdasd",
            tin = "asdasd",
            account_holder_name = "asdasd",
            bank_name = "asdsad",
            bank_swift_code = "adsasd",
            bank_account_number = "sdadssad",
            airline_description = "adsdsad",
            pivot_pricing_for_economy = null,
            pivot_pricing_for_comfort = null,
            pivot_pricing_for_business = null,
            pivot_pricing_for_first = null
        )

        _name.value = airline.name
        _contactNumber.value = airline.contact_number
        _contactEmail.value = airline.contact_email
        _address.value = airline.address
        _tin.value = airline.tin
        _accountHolderName.value = airline.account_holder_name
        _bankName.value = airline.bank_name
        _bankSwiftCode.value = airline.bank_swift_code
        _bankAccountNumber.value = airline.bank_account_number
        _airlineDescription.value = airline.airline_description

    }


    // Function to simulate fetching flights and update _flights
    fun getFlightsForRepresentative() {
        // Simulate updating the flights list
        _flights.value = listOf(
            FlightData(
                flightId = "1",
                airlineId = "1",
                planeId = "737",
                includesFood = true,
                fromAirport = "TAS",
                startDate = "2024-12-10 09:00",
                startDateTimeZone = 5,
                toAirport = "IST",
                isInternationalFlight = true,
                endDate = "2024-12-10 11:30",
                endDateTimeZone = 3,
                baggagePerPerson = 150,
                carryOnBaggagePerPerson = 7,
                isExchangeableForAdditionalFee = true,
                state = "Scheduled"
            )
            // Add more example flights here
        )
    }
}
