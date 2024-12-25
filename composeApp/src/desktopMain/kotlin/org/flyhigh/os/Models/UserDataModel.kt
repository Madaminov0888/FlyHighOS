package org.flyhigh.os.Models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class User(
    @SerializedName("user_id") val userId: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("birthdate") val birthdate: String,
    @SerializedName("passport") val passport: String,
    @SerializedName("foreign_passport") val foreignPassport: String?,
    @SerializedName("citizenship") val citizenship: String,
    @SerializedName("address_country") val addressCountry: String,
    @SerializedName("address_city") val addressCity: String,
    @SerializedName("address_state") val addressState: String,
    @SerializedName("address_line_1") val addressLine1: String,
    @SerializedName("address_line_2") val addressLine2: String?,
    @SerializedName("user_state") val userState: String
)

@Serializable
data class LoginUser(
    val email: String,
    val password: String
)