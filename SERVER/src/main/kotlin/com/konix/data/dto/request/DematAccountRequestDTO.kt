package com.konix.data.dto.request

import com.konix.data.dto.request.enums.NomineeRelation
import kotlinx.serialization.Serializable

@Serializable
data class DematAccountRequestDTO(
    val accountHolderName: String,
    val phoneNumber: String,
    val address: String,
    val panNumber: String,
    val nominee: String,
    val nomineeRelation: NomineeRelation,
    val brokerName: String
)
