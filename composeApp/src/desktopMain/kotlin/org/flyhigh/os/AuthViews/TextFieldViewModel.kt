package org.example.project.AuthViews


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.DateTimePeriod
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


class TextFieldViewModel {

    //login
    private val _loginEmail = MutableStateFlow("")
    val loginEmail: StateFlow<String> get() = _loginEmail

    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> get() = _loginPassword


    //register
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> get() = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> get() = _lastName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _phoneNumber = MutableStateFlow("+998")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _dateOfBirth = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val dateOfBirth: StateFlow<String> get() = _dateOfBirth

    private val _foreignPassId = MutableStateFlow("")
    val foreignPassId: StateFlow<String> get() = _foreignPassId

    private val _idCard = MutableStateFlow("")
    val idCard: StateFlow<String> get() = _idCard

    private val _citizenship = MutableStateFlow("")
    val citizenship: StateFlow<String> get() = _citizenship

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> get() = _city

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _address2 = MutableStateFlow("")
    val address2: StateFlow<String> get() = _address2

    // Update methods for each field
    fun updateLoginEmail(text: String) { _loginEmail.value = text}
    fun updateLoginPassword(text: String) { _loginPassword.value = text}
    fun updateFirstName(text: String) { _firstName.value = text }
    fun updateLastName(text: String) { _lastName.value = text }
    fun updateEmail(text: String) { _email.value = text }
    fun updatePassword(text: String) { _password.value = text }
    fun updatePhoneNumber(text: String) { _phoneNumber.value = text }
    fun updateDateOfBirth(date: String) { _dateOfBirth.value = date }
    fun updateForeignPassId(text: String) { _foreignPassId.value = text }
    fun updateIdCard(text: String) { _idCard.value = text }
    fun updateCitizenship(text: String) { _citizenship.value = text }
    fun updateCity(text: String) { _city.value = text }
    fun updateState(text: String) { _state.value = text }
    fun updateAddress(text: String) { _address.value = text }
    fun updateAddress2(text: String) { _address2.value = text }
}