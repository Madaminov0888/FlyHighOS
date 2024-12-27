package org.flyhigh.os.AdminPanel

import org.flyhigh.os.Models.FlightData
import org.flyhigh.os.Models.RepresentativeData
import org.flyhigh.os.Models.User

class AdminHomeViewModel {

    // Example list for Users
    private val exampleUsers = List(20) { index ->
        User(
            userId = "U${index + 1}",
            email = "user${index + 1}@example.com",
            password = "password${index + 1}",
            lastName = "LastName${index + 1}",
            firstName = "FirstName${index + 1}",
            phoneNumber = "12345678${index}",
            birthdate = "199${index % 10}-0${(index % 9) + 1}-01",
            passport = "P${10000 + index}",
            foreignPassport = if (index % 2 == 0) "FP${100000 + index}" else null,
            citizenship = if (index % 2 == 0) "US" else "UK",
            addressCountry = if (index % 2 == 0) "USA" else "UK",
            addressCity = if (index % 2 == 0) "City${index}" else "Town${index}",
            addressState = if (index % 2 == 0) "State${index}" else "County${index}",
            addressLine1 = "123 Street ${index}",
            addressLine2 = if (index % 3 == 0) "Apt ${index}" else null,
            userState = if (index % 2 == 0) "Active" else "Inactive"
        )
    }

    // Example list for Representatives
    private val exampleRepresentatives = List(20) { index ->
        RepresentativeData(
            representative_id = "R${index + 1}",
            email = "rep${index + 1}@example.com",
            password = "password${index + 1}",
            state = if (index % 2 == 0) "Active" else "Inactive"
        )
    }

    // Example list for Flights
    private val exampleFlights = List(20) { index ->
        FlightData(
            flightId = "F${index + 1}",
            airlineId = "A${index + 1}",
            planeId = "P${index + 1}",
            includesFood = index % 2 == 0,
            fromAirport = "Airport${index}A",
            startDate = "2024-01-${(index % 28) + 1}T10:00:00Z",
            startDateTimeZone = -5,
            toAirport = "Airport${index}B",
            isInternationalFlight = index % 3 == 0,
            endDate = "2024-01-${(index % 28) + 1}T14:00:00Z",
            endDateTimeZone = -8,
            refund = 0.80 - (index * 0.01),
            baggagePerPerson = 20 + (index % 10),
            carryOnBaggagePerPerson = 8 + (index % 5),
            isExchangeableForAdditionalFee = index % 2 == 0,
            state = if (index % 2 == 0) "Scheduled" else "Cancelled"
        )
    }

    // Fetch users
    fun fetchUsers(onResult: (List<User>) -> Unit) {
        // Simulating data fetching
        onResult(exampleUsers)
    }

    // Fetch representatives
    fun fetchRepresentatives(onResult: (List<RepresentativeData>) -> Unit) {
        // Simulating data fetching
        onResult(exampleRepresentatives)
    }

    // Fetch flights
    fun fetchFlights(onResult: (List<FlightData>) -> Unit) {
        // Simulating data fetching
        onResult(exampleFlights)
    }
}
