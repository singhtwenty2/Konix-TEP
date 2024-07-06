package com.konix.data.dto.request.enums

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    OPEN,
    FILLED,
    PARTIALLY_FILLED,
    CANCELLED
}