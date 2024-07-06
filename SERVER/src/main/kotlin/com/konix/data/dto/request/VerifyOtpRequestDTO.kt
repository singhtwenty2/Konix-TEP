package com.konix.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequestDTO(
    val otp: String
)
