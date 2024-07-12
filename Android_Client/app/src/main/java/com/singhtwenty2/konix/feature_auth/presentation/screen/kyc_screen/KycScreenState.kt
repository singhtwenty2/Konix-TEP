package com.singhtwenty2.konix.feature_auth.presentation.screen.kyc_screen

data class KycScreenState(
    val phoneNumber: String = "",
    val address: String = "",
    val aadharNumber: String = "",
    val panNumber: String = "",
    val employmentStatus: String = "",
    val investmentExperience: String = "",
    val riskTolerance: String = "",
    val annualIncome: String = "",
    val isLoading: Boolean = false,
    var error: String = "",

    )
