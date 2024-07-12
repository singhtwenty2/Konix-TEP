package com.singhtwenty2.konix.feature_profile.data.remote.dto

data class KYCDetailsResponseDTO(
    val phoneNumber: String,
    val address: String,
    val aadharNumber: String,
    val panNumber: String,
    val employmentStatus: String,
    val investmentExperience: String,
    val riskTolerance: String,
    val annualIncome: Int
)
