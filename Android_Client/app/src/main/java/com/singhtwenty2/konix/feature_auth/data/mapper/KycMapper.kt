package com.singhtwenty2.konix.feature_auth.data.mapper

import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.KycRequestDTO
import com.singhtwenty2.konix.feature_auth.domain.model.KycRequest

fun KycRequest.toKycRequestDTO(): KycRequestDTO {
    return KycRequestDTO(
        phoneNumber = phoneNumber,
        address = address,
        aadharNumber = aadharNumber,
        panNumber = panNumber,
        employmentStatus = employmentStatus,
        investmentExperience = investmentExperience,
        riskTolerance = riskTolerance,
        annualIncome = annualIncome.toInt()
    )
}