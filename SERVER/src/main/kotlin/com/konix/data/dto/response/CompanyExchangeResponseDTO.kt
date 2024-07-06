package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class CompanyExchangeResponseDTO(
    val id: Int,
    val companyId: Int,
    val exchangeId: Int
)
