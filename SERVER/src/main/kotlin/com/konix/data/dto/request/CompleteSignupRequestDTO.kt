package com.singhtwenty2.data.dto.request

import com.singhtwenty2.data.dto.request.enums.Gender
import kotlinx.serialization.Serializable

@Serializable
data class CompleteSignupRequestDTO(
    val email: String,
    val otp: String,
    val name: String,
    val age: Int,
    val gender: Gender,
    val password: String
)
