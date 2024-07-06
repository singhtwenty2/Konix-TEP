package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DematAccountResponseDTO(
    val dematAccountId: Int,
    val accountNumber: String,
    val accountHolderName: String,
    val address: String,
    val panNumber: String,
    val phoneNumber: String,
    val nominee: String,
    val nomineeRelation: String,
    val openingDate: String,
    val accountStatus: String,
    val brokerName: String,
    val balance: Double
)
