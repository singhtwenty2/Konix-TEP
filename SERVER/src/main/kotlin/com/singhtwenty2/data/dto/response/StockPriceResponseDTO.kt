package com.singhtwenty2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class StockPriceResponseDTO(
    val timeStamp: String,
    val price: Double
)
