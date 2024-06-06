package com.singhtwenty2.data.request

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRequestDTO(
    val id: Int? = null,
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
