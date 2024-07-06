package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponsetDTO(
    val transactionId: Int,
    val userId: Int,
    val companyId: Int,
    val orderId: Int,
    val price: Double,
    val quantity: Int,
    val timeStamp: String
)
