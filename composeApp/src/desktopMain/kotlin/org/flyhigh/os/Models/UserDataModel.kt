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
):RequestDetails


interface RequestDetails

@Serializable
data class RequestModel<T>(
    val request: String,
    val authorization: String?,
    val details: T
)


@Serializable
data class RegisterUserDetails(
    val email: String,
    val password: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("phone_number") val phoneNumber: String,
    val birthdate: String,
    val passport: String,
    @SerialName("foreign_passport") val foreignPassport: String?,
    val citizenship: String,
    @SerialName("address_country") val addressCountry: String,
    @SerialName("address_city") val addressCity: String,
    @SerialName("address_state") val addressState: String,
    @SerialName("address_line_1") val addressLine1: String,
    @SerialName("address_line_2") val addressLine2: String?
) : RequestDetails



@Serializable
data class RepresentativeDetails(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("state") val state: String,
    @SerialName("name") val name: String,
    @SerialName("logo") val logo: String?, // Assuming logo is optional
    @SerialName("contact_number") val contactNumber: String,
    @SerialName("contact_email") val contactEmail: String,
    @SerialName("address") val address: String,
    @SerialName("tin") val tin: String,
    @SerialName("account_holder_name") val accountHolderName: String,
    @SerialName("bank_name") val bankName: String,
    @SerialName("bank_swift_code") val bankSwiftCode: String,
    @SerialName("bank_account_number") val bankAccountNumber: String,
    @SerialName("airline_description") val airlineDescription: String
):RequestDetails


@Serializable
data class RepresentativeData(
    val representative_id: String,
    val email: String,
    val password: String,
    val state: String
)