package com.singhtwenty2.konix.feature_home.domain.model

data class ExchangeDetailsResponse(
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
