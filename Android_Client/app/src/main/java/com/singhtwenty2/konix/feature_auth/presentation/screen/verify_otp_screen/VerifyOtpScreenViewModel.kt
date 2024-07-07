package com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(VerifyOtpState())
    private val resultChannel = Channel<AuthResponseHandler<Unit>>()
    val otpResult = resultChannel.receiveAsFlow()

    fun onEvent(event: VerifyOtpUiEvent) {
        when(event) {
            is VerifyOtpUiEvent.OtpChanged -> {
                state.value = state.value.copy(otp = event.otp)
            }
            VerifyOtpUiEvent.ResendOtpClicked -> TODO()
            VerifyOtpUiEvent.VerifyOtpClicked -> verifyOtp()
        }
    }

    private fun verifyOtp() {
        viewModelScope.launch {
            state.value = state.value.copy(isOtpVerifyLoading = true)
            val result = authRepository.verifyOtp(
                VerifyOtpRequest(
                    otp = state.value.otp
                )
            )
            resultChannel.send(result)
            state.value = state.value.copy(isOtpVerifyLoading = false)
        }
    }
}