package org.flyhigh.os.Models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel<T>(
    val status: String,
    val result: T
)

interface ResponseModels

@Serializable
data class LoginResponseModel(
    val token: String
):ResponseModels