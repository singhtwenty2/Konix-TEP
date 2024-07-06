package com.singhtwenty2.konix.feature_auth.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)
