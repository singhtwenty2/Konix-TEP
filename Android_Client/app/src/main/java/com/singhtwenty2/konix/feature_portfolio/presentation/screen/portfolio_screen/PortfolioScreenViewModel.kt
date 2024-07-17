package com.singhtwenty2.konix.feature_portfolio.presentation.screen.portfolio_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_portfolio.domain.repository.PortFolioRepository
import com.singhtwenty2.konix.feature_portfolio.util.PortfolioResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioScreenViewModel @Inject constructor(
    private val portFolioRepository: PortFolioRepository
): ViewModel() {

    var state = mutableStateOf(PortfolioScreenState())

    init {
        getPortfolioDetails()
    }

    fun onEvent(event: PortfolioScreenUiEvent) {
        when(event) {
            PortfolioScreenUiEvent.OnRefresh -> getPortfolioDetails()
        }
    }

    private fun getPortfolioDetails() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            when(val result = portFolioRepository.getAllCompaniesInPortfolio()) {
                is PortfolioResponseHandler.Success -> {
                    state.value = state.value.copy(isError = false)
                    val data = result.data
                    data?.let {
                        state.value = state.value.copy(portfolio = data)
                    }
                }
                is PortfolioResponseHandler.BadRequest -> {
                    state.value = state.value.copy(
                        isError = true,
                        error = "Bad Request"
                    )
                }
                is PortfolioResponseHandler.InternalServerError -> {
                    state.value = state.value.copy(
                        isError = true,
                        error = "Internal Server Error"
                    )
                }
                is PortfolioResponseHandler.UnAuthorized -> {
                    state.value = state.value.copy(
                        isError = true,
                        error = "UnAuthorized"
                    )
                }
                is PortfolioResponseHandler.UnknownError -> {
                    state.value = state.value.copy(
                        isError = true,
                        error = "UnknownError"
                    )
                }
            }
            state.value = state.value.copy(isLoading = false)
        }
    }
}