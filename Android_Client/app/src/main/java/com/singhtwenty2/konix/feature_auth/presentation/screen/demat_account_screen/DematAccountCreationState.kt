package com.singhtwenty2.konix.feature_auth.presentation.screen.demat_account_screen

data class DematAccountCreationState(
    val accountHolderName: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val panNumber: String = "",
    val nominee: String = "",
    val nomineeRelation: String = "",
    val brokerName: String = "",
    val isLoading: Boolean = false,
    var error: String = "",
)
