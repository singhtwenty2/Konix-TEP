package com.konix.data.dto.request

import com.konix.data.dto.request.enums.Gender
import kotlinx.serialization.Serializable

@Serializable
data class SignupSessionRequestDTO(
    val email: String,
    val name: String,
    val age: Int,
    val gender: Gender,
    val password: String
)
