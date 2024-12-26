package org.flyhigh.os.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_id") val userId: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("birthdate") val birthdate: String,
    @SerialName("passport") val passport: String,
    @SerialName("foreign_passport") val foreignPassport: String?,
    @SerialName("citizenship") val citizenship: String,
    @SerialName("address_country") val addressCountry: String,
    @SerialName("address_city") val addressCity: String,
    @SerialName("address_state") val addressState: String,
    @SerialName("address_line_1") val addressLine1: String,
    @SerialName("address_line_2") val addressLine2: String?,
    @SerialName("user_state") val userState: String
)

@Serializable
data class LoginUser(
    val email: String,
    val password: String
)