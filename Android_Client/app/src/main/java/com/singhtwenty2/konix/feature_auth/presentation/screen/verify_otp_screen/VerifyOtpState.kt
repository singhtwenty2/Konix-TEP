package com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen

data class VerifyOtpState(
    val otp: String = "",
    val isOtpValid: Boolean = false,
    val isOtpResendEnabled: Boolean = false,
    val isOtpVerifyEnabled: Boolean = false,
    val isOtpVerifyLoading: Boolean = false,
    val isOtpResendLoading: Boolean = false,
    val isOtpVerifySuccess: Boolean = false,
    val isOtpResendSuccess: Boolean = false,
    val isOtpVerifyError: Boolean = false,
    val isOtpResendError: Boolean = false,
    val otpVerifyErrorMessage: String = "",
    val otpResendErrorMessage: String = ""
)
