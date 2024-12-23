package org.flyhigh.os.RepresentativePanel.Auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RepresentativeAuthViewModel {

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

    private val _expirationDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val expirationDate: StateFlow<String> get() = _expirationDate

    private val _tin = MutableStateFlow("")
    val tin: StateFlow<String> get() = _tin

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> get() = _companyName


    private val _companyAddress = MutableStateFlow("")
    val companyAddress: StateFlow<String> get() = _companyAddress

    private val _bankName = MutableStateFlow("")
    val bankName: StateFlow<String> get() = _bankName

    private val _holderName = MutableStateFlow("")
    val holderName: StateFlow<String> get() = _holderName

    private val _bankAccountNumber = MutableStateFlow("")
    val bankAccountNumber: StateFlow<String> get() = _bankAccountNumber

    private val _swiftCode = MutableStateFlow("")
    val SWIFTcode: StateFlow<String> get() = _swiftCode

    private val _logoVar = MutableStateFlow("")
    val logoVar: StateFlow<String> get() = _logoVar

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
    fun updateExpirationDate(date: String) {
        _expirationDate.value = date
        checkRegister()
    }
    fun updateTin(text: String) {
        _tin.value = text
        checkRegister()
    }
    fun updateCompanyName(text: String) {
        _companyName.value = text
        checkRegister()
    }
    fun updateCompanyAddress(text: String) {
        _companyAddress.value = text
        checkRegister()
    }
    fun updateBankName(text: String) {
        _bankName.value = text
        checkRegister()
    }
    fun updateHolderName(text: String) {
        _holderName.value = text
        checkRegister()
    }
    fun updateBankAccountNumber(text: String) {
        _bankAccountNumber.value = text
        checkRegister()
    }
    fun updateSWIFTcode(text: String) {
        _swiftCode.value = text
        checkRegister()
    }



    //TODO network functions
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
            !companyName.value.isEmpty() and
            !companyAddress.value.isEmpty() and
            !tin.value.isEmpty() and
            !expirationDate.value.isEmpty() and
            !companyName.value.isEmpty() and
            !bankName.value.isEmpty() and
            !holderName.value.isEmpty() and
            !bankAccountNumber.value.isEmpty()
        ) {
            print("check complete")
            _canRegister.value = true
        }
    }
}