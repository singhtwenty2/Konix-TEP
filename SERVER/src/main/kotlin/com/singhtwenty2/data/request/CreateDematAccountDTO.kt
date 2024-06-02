package com.singhtwenty2.data.request

import com.singhtwenty2.data.request.enums.NomineeRelation
import kotlinx.serialization.Serializable

@Serializable
data class CreateDematAccountDTO(
    val accountHolderName: String,
    val phoneNumber: String,
    val address: String,
    val panNumber: String,
    val nominee: String,
    val nomineeRelation: NomineeRelation,
    val brokerName: String
)
