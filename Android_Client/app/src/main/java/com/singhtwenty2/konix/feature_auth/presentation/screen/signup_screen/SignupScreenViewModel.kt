package com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(SignupState())

    private val resultChannel = Channel<AuthResponseHandler<Unit>>()
    val signupResult = resultChannel.receiveAsFlow()

    fun onEvent(event: SignupUiEvent) {
        when (event) {
            is SignupUiEvent.NameChanged -> {
                state.value = state.value.copy(name = event.name)
            }

            is SignupUiEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }

            is SignupUiEvent.AgeChanged -> {
                state.value = state.value.copy(age = event.age)
            }
            is SignupUiEvent.ConfirmPasswordChanged -> {
                state.value = state.value.copy(confirmPassword = event.confirmPassword)
            }
            is SignupUiEvent.GenderChanged -> {
                state.value = state.value.copy(gender = event.gender)
            }
            is SignupUiEvent.PasswordChanged -> {
                state.value = state.value.copy(password = event.password)
            }
            SignupUiEvent.SignupClicked -> signup()
        }
    }

    private fun signup() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = authRepository.signup(
                SignupRequest(
                    name = state.value.name,
                    email = state.value.email,
                    age = (state.value.age).toInt(),
                    gender = state.value.gender,
                    password = state.value.password
                )
            )
            resultChannel.send(result)
            state.value = state.value.copy(isLoading = false)
        }
    }
}