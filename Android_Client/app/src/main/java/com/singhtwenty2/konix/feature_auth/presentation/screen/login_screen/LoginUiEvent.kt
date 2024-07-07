package com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object LoginClicked : LoginUiEvent()
}