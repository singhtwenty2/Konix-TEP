package com.singhtwenty2.konix.feature_home.presentation.screen.stock_chart_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse
import com.singhtwenty2.konix.feature_home.domain.repository.CompanyRepository
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockChartViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    val state = mutableStateOf(StockChartScreenState())

    private val stockPriceResultChannel =
        Channel<CompanyResponseHandler<List<StockPriceResponse>>>()
    val stockPriceResult = stockPriceResultChannel.receiveAsFlow()

    private val historicalStockPriceResultChannel =
        Channel<CompanyResponseHandler<List<StockPriceResponse>>>()
    val historicalStockPriceResult = historicalStockPriceResultChannel.receiveAsFlow()

    private var periodicRefreshJob: Job? = null

    init {
        startPeriodicRefresh()
    }

    private fun startPeriodicRefresh() {
        periodicRefreshJob = viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    kotlinx.coroutines.delay(1000)
                }
            }.collect {
                getStockPriceByCompanyId(currentCompanyId)
            }
        }
    }

    private fun stopPeriodicRefresh() {
        periodicRefreshJob?.cancel()
    }

    private var currentCompanyId: Int = 0

    fun onEvent(event: StockChartUiEvent) {
        when (event) {
            is StockChartUiEvent.OnRefresh -> {
                currentCompanyId = event.companyId
                getStockPriceByCompanyId(event.companyId)
            }

            is StockChartUiEvent.OnCumulativePriceFetch -> {
                state.value = state.value.copy(isCumulativePriceFetch = event.isCumulative)
                if (event.isCumulative) {
                    stopPeriodicRefresh()
                    getHistoricalStockPriceByCompanyId(currentCompanyId)
                } else {
                    startPeriodicRefresh()
                }
            }
        }
    }

    private fun getStockPriceByCompanyId(companyId: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val response = companyRepository.getStockPriceByCompanyId(companyId)
            stockPriceResultChannel.send(response)
            state.value = state.value.copy(isLoading = false)
        }
    }
    private fun getHistoricalStockPriceByCompanyId(companyId: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val response = companyRepository.getHistoricalStockPriceByCompanyId(companyId)
            historicalStockPriceResultChannel.send(response)
            state.value = state.value.copy(isLoading = false)
        }
    }
}
