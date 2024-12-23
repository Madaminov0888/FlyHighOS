package org.flyhigh.os.AdminPanel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminAuthViewModel {
    private val _login = MutableStateFlow("")
    val login: StateFlow<String> get() = _login

    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> get() = _loginPassword

    private val _canLogin = MutableStateFlow(false)
    val canLogin: StateFlow<Boolean> get() = _canLogin

    fun updateLogin(text: String) {
        _login.value = text
        checkLogin()
    }
    fun updateLoginPassword(text: String) {
        _loginPassword.value = text
        checkLogin()
    }

    fun checkLogin() {
        if (!login.value.isEmpty() and !loginPassword.value.isEmpty()) {
            _canLogin.value = true
        }
    }
}