package com.singhtwenty2.konix.feature_profile.domain.model

data class KYCDetailsResponse(
    val phoneNumber: String,
    val address: String,
    val aadharNumber: String,
    val panNumber: String,
    val employmentStatus: String,
    val investmentExperience: String,
    val riskTolerance: String,
    val annualIncome: Int
)
