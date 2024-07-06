package com.konix.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequestDTO(
    val userId: Int,
    val companyId: Int,
    val orderId: Int,
    val price: Double,
    val quantity: Int
)
