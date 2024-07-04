package com.singhtwenty2.data.dto.response

import com.singhtwenty2.data.dto.request.enums.EmploymentStatus
import com.singhtwenty2.data.dto.request.enums.InvestmentExperience
import com.singhtwenty2.data.dto.request.enums.RiskTolerance
import kotlinx.serialization.Serializable

@Serializable
data class KYCDetailsResponseDTO(
    val phoneNumber: String,
    val address: String,
    val aadharNumber: String,
    val panNumber: String,
    val employmentStatus: EmploymentStatus,
    val investmentExperience: InvestmentExperience,
    val riskTolerance: RiskTolerance,
    val annualIncome: Int
)
