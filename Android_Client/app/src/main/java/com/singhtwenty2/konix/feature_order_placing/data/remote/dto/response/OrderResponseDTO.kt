package com.singhtwenty2.konix.feature_order_placing.data.remote.dto.response

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
