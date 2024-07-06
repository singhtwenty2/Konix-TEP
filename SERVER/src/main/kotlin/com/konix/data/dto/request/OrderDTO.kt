package com.konix.data.dto.request

import com.konix.data.dto.request.enums.OrderStatus
import com.konix.data.dto.request.enums.OrderType
import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val orderId: Int,
    val userId: Int,
    val companyId: Int,
    val orderType: OrderType,
    val orderStatus: OrderStatus,
    val price: Double,
    var quantity: Int
)
