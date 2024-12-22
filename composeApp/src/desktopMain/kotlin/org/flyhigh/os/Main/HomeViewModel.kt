package org.flyhigh.os.Main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel {

    private val _fromCity = MutableStateFlow("")
    val fromCity: StateFlow<String> get() = _fromCity

    private val _toCity = MutableStateFlow("")
    val toCity: StateFlow<String> get() = _toCity

    private val _flightDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val flightDate: StateFlow<String> get() = _flightDate

    private val _returnDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val returnDate: StateFlow<String> get() = _returnDate

    private val _adultNumber = MutableStateFlow("")
    val adultNumber: StateFlow<String> get() = _adultNumber

    private val _childrenNumber = MutableStateFlow("")
    val childrenNumber: StateFlow<String> get() = _childrenNumber


    //Functions
    fun updateToCity(text: String) { _toCity.value = text }
    fun updateFromCity(text: String) { _fromCity.value = text}
    fun updateFlightDate(date: String) { _flightDate.value = date }
    fun updateReturnDate(date: String) { _returnDate.value = date }
    fun updateChildrenNumber(text: String) { _childrenNumber.value = text }
    fun updateAdultNumber(text: String) { _adultNumber.value = text }

}