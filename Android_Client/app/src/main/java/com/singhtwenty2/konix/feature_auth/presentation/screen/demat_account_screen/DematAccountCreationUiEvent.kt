package com.singhtwenty2.konix.feature_auth.presentation.screen.demat_account_screen

sealed class DematAccountCreationUiEvent {
    data class AccountHolderNameChanged(val accountHolderName: String) : DematAccountCreationUiEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : DematAccountCreationUiEvent()
    data class AddressChanged(val address: String) : DematAccountCreationUiEvent()
    data class PanNumberChanged(val panNumber: String) : DematAccountCreationUiEvent()
    data class NomineeChanged(val nominee: String) : DematAccountCreationUiEvent()
    data class NomineeRelationChanged(val nomineeRelation: String) : DematAccountCreationUiEvent()
    data class BrokerNameChanged(val brokerName: String) : DematAccountCreationUiEvent()
    data object CreateAccountClicked : DematAccountCreationUiEvent()
}