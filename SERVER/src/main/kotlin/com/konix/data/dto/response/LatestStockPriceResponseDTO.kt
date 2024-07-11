package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LatestStockPriceResponseDTO(
    val price: Double
)
