package com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen

data class SignupState(
    val name: String = "",
    val email: String = "",
    val age : String = "",
    val gender: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
