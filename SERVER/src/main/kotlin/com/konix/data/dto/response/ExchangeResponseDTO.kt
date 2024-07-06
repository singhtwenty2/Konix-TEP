package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeResponseDTO(
    val id: Int,
    val name: String,
    val symbol: String,
    val description: String,
    val location: String,
    val website: String,
    val timeZone: String,
    val openingHours: String,
    val closingHours: String,
    val currency: String,
    val establishedDate: String
)
