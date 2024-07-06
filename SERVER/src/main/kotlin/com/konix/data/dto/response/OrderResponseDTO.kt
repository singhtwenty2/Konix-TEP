package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDTO(
    val orderId: Int,
    val userId: Int,
    val companyId: Int,
    val orderType: String,
    val orderStatus: String,
    val price: Double,
    var quantity: Int,
    val createdAt: String,
    val updatedAt: String
)
