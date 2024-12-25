package org.flyhigh.os.Main.ProfilePage

import org.flyhigh.os.Models.User

class ProfileViewModel {
    var mainUser: User? = null

    init {
        getUser()
    }

    private fun getUser() {
        val user = User(
            userId = "12345",
            email = "example@example.com",
            password = "password123",
            lastName = "Doe",
            firstName = "John",
            phoneNumber = "+1234567890",
            birthdate = "1990-01-01",
            passport = "AB123456",
            foreignPassport = "DD123456",
            citizenship = "USA",
            addressCountry = "USA",
            addressCity = "New York",
            addressState = "NY",
            addressLine1 = "123 Main St",
            addressLine2 = null,
            userState = "Active"
        )
        mainUser = user
    }

    fun checkUser(action: () -> Unit) {
        getUser()
        //will check user
        if (mainUser == null) {
            action()
        }
    }

}