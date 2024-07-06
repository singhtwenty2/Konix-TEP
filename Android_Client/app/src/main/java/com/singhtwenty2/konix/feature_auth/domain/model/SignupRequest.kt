package com.singhtwenty2.konix.feature_auth.domain.model

data class SignupRequest(
    val name: String,
    val email: String,
    val age: Int,
    val gender: String,
    val password: String
)
