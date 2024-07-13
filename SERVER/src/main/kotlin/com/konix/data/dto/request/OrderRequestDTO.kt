package com.konix.data.dto.request

import com.konix.data.dto.request.enums.OrderType
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDTO(
    val companyId: Int,
    val orderType: OrderType,
    val price: Double,
    var quantity: Int
)
