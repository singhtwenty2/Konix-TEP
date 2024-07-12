package com.singhtwenty2.konix.feature_auth.domain.model

data class DematAccountCreationRequest(
    val accountHolderName: String,
    val phoneNumber: String,
    val address: String,
    val panNumber: String,
    val nominee: String,
    val nomineeRelation: String,
    val brokerName: String
)
