package com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen

sealed class SignupUiEvent {
    data class NameChanged(val name: String) : SignupUiEvent()
    data class EmailChanged(val email: String) : SignupUiEvent()
    data class AgeChanged(val age: String) : SignupUiEvent()
    data class GenderChanged(val gender: String) : SignupUiEvent()
    data class PasswordChanged(val password: String) : SignupUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignupUiEvent()
    data object SignupClicked : SignupUiEvent()
}
