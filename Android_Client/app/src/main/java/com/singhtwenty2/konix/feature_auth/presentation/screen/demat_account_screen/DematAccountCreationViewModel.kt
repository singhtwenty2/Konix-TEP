package com.singhtwenty2.konix.feature_auth.presentation.screen.demat_account_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_auth.domain.model.DematAccountCreationRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DematAccountCreationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(DematAccountCreationState())
    private val dematChannel = Channel<AuthResponseHandler<Unit>>()
    val dematResult = dematChannel.receiveAsFlow()

    fun onEvent(event: DematAccountCreationUiEvent) {
        when(event) {
            is DematAccountCreationUiEvent.AccountHolderNameChanged -> {
                state.value = state.value.copy(accountHolderName = event.accountHolderName)
            }
            is DematAccountCreationUiEvent.AddressChanged -> {
                state.value = state.value.copy(address = event.address)
            }
            is DematAccountCreationUiEvent.BrokerNameChanged -> {
                state.value = state.value.copy(brokerName = event.brokerName)
            }
            is DematAccountCreationUiEvent.NomineeChanged -> {
                state.value = state.value.copy(nominee = event.nominee)
            }
            is DematAccountCreationUiEvent.NomineeRelationChanged -> {
                state.value = state.value.copy(nomineeRelation = event.nomineeRelation)
            }
            is DematAccountCreationUiEvent.PanNumberChanged -> {
                state.value = state.value.copy(panNumber = event.panNumber)
            }
            is DematAccountCreationUiEvent.PhoneNumberChanged -> {
                state.value = state.value.copy(phoneNumber = event.phoneNumber)
            }
            DematAccountCreationUiEvent.CreateAccountClicked -> createDematAccount()
        }
    }
    private fun createDematAccount() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = authRepository.performDematCreation(
                DematAccountCreationRequest(
                    accountHolderName = state.value.accountHolderName,
                    phoneNumber = state.value.phoneNumber,
                    address = state.value.address,
                    panNumber = state.value.panNumber,
                    nominee = state.value.nominee,
                    nomineeRelation = state.value.nomineeRelation,
                    brokerName = state.value.brokerName
                )
            )
            dematChannel.send(result)
            state.value = state.value.copy(isLoading = false)
        }
    }
}