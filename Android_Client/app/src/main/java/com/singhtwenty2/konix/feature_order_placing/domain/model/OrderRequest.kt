package com.singhtwenty2.konix.feature_order_placing.domain.model

data class OrderRequest(
    val companyId: Int,
    val orderType: String,
    val price: Double,
    var quantity: Int
)
