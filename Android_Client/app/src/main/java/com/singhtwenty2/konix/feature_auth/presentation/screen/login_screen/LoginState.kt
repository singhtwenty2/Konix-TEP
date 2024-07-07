package com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
