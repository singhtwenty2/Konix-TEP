package com.singhtwenty2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String
)
