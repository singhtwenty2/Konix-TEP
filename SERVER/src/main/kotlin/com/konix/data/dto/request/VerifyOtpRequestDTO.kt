package com.konix.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteSignupRequestDTO(
    val email: String,
    val otp: String,
)
