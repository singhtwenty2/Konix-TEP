package com.singhtwenty2.konix.feature_auth.presentation.screen.kyc_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_auth.domain.model.KycRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KycScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(KycScreenState())
    private val kycChannel = Channel<AuthResponseHandler<Unit>>()
    val kycResult = kycChannel.receiveAsFlow()

    fun onEvent(event: KycUiEvent) {
        when(event) {
            is KycUiEvent.AadharNumberChanged -> {
                state.value = state.value.copy(aadharNumber = event.aadharNumber)
            }
            is KycUiEvent.AddressChanged -> {
                state.value = state.value.copy(address = event.address)
            }
            is KycUiEvent.AnnualIncomeChanged -> {
                state.value = state.value.copy(annualIncome = event.annualIncome)
            }
            is KycUiEvent.EmploymentStatusChanged -> {
                state.value = state.value.copy(employmentStatus = event.employmentStatus)
            }
            is KycUiEvent.InvestmentExperienceChanged -> {
                state.value = state.value.copy(investmentExperience = event.investmentExperience)
            }
            is KycUiEvent.PanNumberChanged -> {
                state.value = state.value.copy(panNumber = event.panNumber)
            }
            is KycUiEvent.PhoneNumberChanged -> {
                state.value = state.value.copy(phoneNumber = event.phoneNumber)
            }
            is KycUiEvent.RiskToleranceChanged -> {
                state.value = state.value.copy(riskTolerance = event.riskTolerance)
            }
            KycUiEvent.SubmitKyc -> submitKyc()
        }
    }
    private fun submitKyc() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = authRepository.performKyc(
                KycRequest(
                    phoneNumber = state.value.phoneNumber,
                    address = state.value.address,
                    aadharNumber = state.value.aadharNumber,
                    panNumber = state.value.panNumber,
                    employmentStatus = state.value.employmentStatus,
                    investmentExperience = state.value.investmentExperience,
                    riskTolerance = state.value.riskTolerance,
                    annualIncome = state.value.annualIncome
                )
            )
            kycChannel.send(result)
            state.value = state.value.copy(isLoading = false)
        }
    }
}