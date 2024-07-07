package com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen

sealed class VerifyOtpUiEvent {
    data class OtpChanged(val otp: String) : VerifyOtpUiEvent()
    data object VerifyOtpClicked : VerifyOtpUiEvent()
    data object ResendOtpClicked : VerifyOtpUiEvent()
}