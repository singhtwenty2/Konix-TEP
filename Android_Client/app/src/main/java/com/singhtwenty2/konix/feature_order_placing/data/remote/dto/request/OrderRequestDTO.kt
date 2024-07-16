package com.singhtwenty2.konix.feature_order_placing.data.remote.dto.request

data class OrderRequestDTO(
    val companyId: Int,
    val orderType: String,
    val price: Double,
    var quantity: Int
)
