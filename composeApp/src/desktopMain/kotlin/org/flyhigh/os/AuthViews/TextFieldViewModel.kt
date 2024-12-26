package org.example.project.AuthViews


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import org.flyhigh.os.Managers.NetworkManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class TextFieldViewModel {

    var citizenships: Array<String> = emptyArray()

    private val networkManager = NetworkManager.getInstance()

    init {
        getListOfCitizenships()
    }

    private val _canLogin = MutableStateFlow(false)
    val canLogin: StateFlow<Boolean> get() = _canLogin

    private val _canRegister = MutableStateFlow(false)
    val canRegister: StateFlow<Boolean> get() = _canRegister

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

    private val _country = MutableStateFlow("")
    val country: StateFlow<String> get() = _country

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> get() = _city

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _address2 = MutableStateFlow("")
    val address2: StateFlow<String> get() = _address2

    // Update methods for each field
    fun updateLoginEmail(text: String) {
        _loginEmail.value = text
        checkLogin()
    }
    fun updateLoginPassword(text: String) {
        _loginPassword.value = text
        checkLogin()
    }
    fun updateFirstName(text: String) {
        _firstName.value = text
        checkRegister()
    }
    fun updateLastName(text: String) {
        _lastName.value = text
        checkRegister()
    }
    fun updateEmail(text: String) {
        _email.value = text
        checkRegister()
    }
    fun updatePassword(text: String) {
        _password.value = text
        checkRegister()
    }
    fun updatePhoneNumber(text: String) {
        _phoneNumber.value = text
        checkRegister()
    }
    fun updateDateOfBirth(date: String) {
        _dateOfBirth.value = date
        checkRegister()
    }
    fun updateForeignPassId(text: String) {
        _foreignPassId.value = text
        checkRegister()
    }
    fun updateIdCard(text: String) {
        _idCard.value = text
        checkRegister()
    }
    fun updateCitizenship(text: String) {
        _citizenship.value = text
        checkRegister()
    }
    fun updateCountry(text: String) {
        _country.value = text
        checkRegister()
    }
    fun updateCity(text: String) {
        _city.value = text
        checkRegister()
    }
    fun updateState(text: String) {
        _state.value = text
        checkRegister()
    }
    fun updateAddress(text: String) {
        _address.value = text
        checkRegister()
    }
    fun updateAddress2(text: String) {
        _address2.value = text
        checkRegister()
    }



    //TODO network functions

    fun getListOfCitizenships() {
        citizenships = citizenships.plus("Uzbekistan")
            .plus("Russia")
            .plus("UK")
            .plus("USA")
            .plus("China")
            .plus("Germany")
            .plus("France")
            .plus("Spain")
    }

    fun checkLogin() {
        if (!loginEmail.value.isEmpty() and !loginPassword.value.isEmpty()) {
            _canLogin.value = true
        }
    }

    fun checkRegister() {
        print("checking")
        if (
            !firstName.value.isEmpty() and
            !lastName.value.isEmpty() and
            !email.value.isEmpty() and
            !password.value.isEmpty() and
            !phoneNumber.value.isEmpty() and
            !foreignPassId.value.isEmpty() and
            !idCard.value.isEmpty() and
            !citizenship.value.isEmpty() and
            !country.value.isEmpty() and
            !city.value.isEmpty() and
            !state.value.isEmpty() and
            !address.value.isEmpty() and
            !address2.value.isEmpty()
        ) {
            print("check complete")
            _canRegister.value = true
        }
    }



}



@Serializable
data class UserLogin(
    val login: String,
    val password: String
)