package com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.UserStatusResponse
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(LoginState())

    private val resultChannel = Channel<AuthResponseHandler<Unit>>()
    val loginResult = resultChannel.receiveAsFlow()

    private val userStatusChannel = Channel<
            AuthResponseHandler<
                    UserStatusResponse
                    >>()
    val userStatusResult = userStatusChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }

            is LoginUiEvent.PasswordChanged -> {
                state.value = state.value.copy(password = event.password)
            }

            LoginUiEvent.LoginClicked -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = authRepository.login(
                LoginRequest(
                    email = state.value.email,
                    password = state.value.password
                )
            )
            resultChannel.send(result)
            if (result is AuthResponseHandler.Success) {
                fetchKycDematStatus()
            } else {
                state.value = state.value.copy(isLoading = false)
            }
        }
    }

    private fun fetchKycDematStatus() {
        viewModelScope.launch {
            val userStatus = authRepository.getUserStatus()
            userStatusChannel.send(userStatus)
            state.value = state.value.copy(isLoading = false)
        }
    }
}