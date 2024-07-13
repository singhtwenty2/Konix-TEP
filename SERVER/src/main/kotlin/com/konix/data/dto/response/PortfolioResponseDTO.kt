package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioResponseDTO(
    val companyId: Int,
    val companyName: String,
    val quantity: Int
)
