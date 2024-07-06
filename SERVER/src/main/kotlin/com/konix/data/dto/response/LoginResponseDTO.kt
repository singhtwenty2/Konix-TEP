package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String
)
