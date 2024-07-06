package com.singhtwenty2.konix.feature_auth.data.remote.dto.request

data class SignupRequestDTO(
    val name: String,
    val email: String,
    val age: Int,
    val gender: String,
    val password: String
)
