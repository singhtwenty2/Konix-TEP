package com.singhtwenty2.konix.feature_auth.data.remote.dto.request

data class DematAccountCreationRequestDTO(
    val accountHolderName: String,
    val phoneNumber: String,
    val address: String,
    val panNumber: String,
    val nominee: String,
    val nomineeRelation: String,
    val brokerName: String
)
