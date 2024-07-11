package com.singhtwenty2.konix.feature_home.presentation.screen.exchange_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_home.domain.repository.CompanyRepository
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    var state = mutableStateOf(ExchangeScreenState())

    fun getExchangeDetails(exchangeId: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = companyRepository.getDetailsAboutExchange(exchangeId)
            state.value = state.value.copy(isLoading = false)
            when (result) {
                is CompanyResponseHandler.Success -> {
                    state.value = state.value.copy(exchangeDetail = result.data)
                }

                is CompanyResponseHandler.BadRequest -> {
                    state.value = state.value.copy(error = "Bad Request")
                }

                is CompanyResponseHandler.InternalServerError -> {
                    state.value = state.value.copy(error = "Internal Server Error")
                }

                is CompanyResponseHandler.UnAuthorized -> {
                    state.value = state.value.copy(error = "UnAuthorized")
                }

                is CompanyResponseHandler.UnknownError -> {
                    state.value = state.value.copy(error = "Unknown Error")
                }
            }
        }
    }
}