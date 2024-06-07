package com.singhtwenty2.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CompanyExchangeResponseDTO(
    val id: Int,
    val companyId: Int,
    val exchangeId: Int
)
