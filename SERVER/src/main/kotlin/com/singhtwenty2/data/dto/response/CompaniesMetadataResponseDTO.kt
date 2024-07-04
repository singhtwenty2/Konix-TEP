package com.singhtwenty2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class CompaniesMetadataResponseDTO(
    val id: Int,
    val name: String,
    val symbol: String,
    val marketCap: Double
)
