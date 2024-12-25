package org.flyhigh.os.Main

import org.flyhigh.os.Models.FlightData
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Extensions {

    fun calculateDuration(flights: List<FlightData>): String {
        val startTime = flights.first().startDate
        val endTime = flights.last().endDate
        return "$startTime - $endTime"
    }

    fun formatTime(dateTime: String): String {
        return dateTime.substring(11, 16)  // Extracts hour and minute (HH:mm)
    }

    fun formatDate(dateTime: String): String {
        return dateTime.substring(0, 10)  // Extracts date (YYYY-MM-DD)
    }

    fun calculateSegmentDurations(flights: List<FlightData>): List<String> {
        val durations = mutableListOf<String>()

        for (i in 0 until flights.size - 1) {
            val flight = flights[i]
            val nextFlight = flights[i + 1]

            val segmentDuration = calculateTimeDifference(flight.startDate, flight.endDate)
            val layoverDuration = calculateTimeDifference(flight.endDate, nextFlight.startDate)

            durations.add(segmentDuration)
            durations.add("Layover ${layoverDuration}")
        }

        val finalFlight = flights.last()
        durations.add(calculateTimeDifference(finalFlight.startDate, finalFlight.endDate))

        return durations
    }


    fun calculateTimeDifference(start: String, end: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        try {
            val startDate = format.parse(start)
            val endDate = format.parse(end)

            if (startDate != null && endDate != null) {
                val diffInMillis = endDate.time - startDate.time
                val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

                return "${hours}h ${minutes}m"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Invalid date format"
    }
}