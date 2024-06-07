package com.singhtwenty2.data.response

import kotlinx.serialization.Serializable

@Serializable
data class StockPriceResponseDTO(
    val timeStamp: String,
    val price: Double
)
