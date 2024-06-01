package com.singhtwenty2.data.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String
)
