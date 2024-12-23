package org.flyhigh.os.Main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.swing.plaf.nimbus.State

class HomeViewModel {

    private val _fromCity = MutableStateFlow("")
    val fromCity: StateFlow<String> get() = _fromCity

    private val _toCity = MutableStateFlow("")
    val toCity: StateFlow<String> get() = _toCity

    private val _flightDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val flightDate: StateFlow<String> get() = _flightDate

    private val _returnDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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

}