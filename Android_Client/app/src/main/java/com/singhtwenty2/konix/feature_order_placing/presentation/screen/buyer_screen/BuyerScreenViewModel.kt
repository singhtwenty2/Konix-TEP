package com.singhtwenty2.konix.feature_order_placing.presentation.screen.buyer_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderRequest
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse
import com.singhtwenty2.konix.feature_order_placing.domain.repository.OrderRepository
import com.singhtwenty2.konix.feature_order_placing.util.OrderResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerScreenViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    var state = mutableStateOf(BuyerScreenState())

    private val orderChannel = Channel<OrderResponseHandler<OrderResponse>>()
    val orderResult = orderChannel.receiveAsFlow()

    fun startFetchingPrice(companyId: Int) {
        viewModelScope.launch {
            while (true) {
                fetchPrice(companyId)
                delay(1000)
            }
        }
    }

    suspend fun onEvent(event: BuyerUiEvent) {
        when (event) {
            is BuyerUiEvent.PriceChanged -> {
                updateState { copy(price = event.price) }
            }

            is BuyerUiEvent.QuantityChanged -> {
                updateState { copy(quantity = event.quantity) }
            }

            is BuyerUiEvent.Refreshed -> {
                fetchPrice(event.companyId)
            }

            is BuyerUiEvent.BuyClicked -> {
                placeMarketOrder(
                    companyId = event.companyId,
                    orderType = event.orderType
                )
            }

            is BuyerUiEvent.LimitPriceChanged -> {
                updateState { copy(limitPrice = event.limitPrice) }
            }

            is BuyerUiEvent.LimitOrderTriggered -> {
                updateState { copy(isLimitOrder = event.isLimitOrder) }
            }

            is BuyerUiEvent.LimitOrderPlaced -> {
                placeLimitOrder(
                    companyId = event.companyId,
                    orderType = event.orderType
                )
            }
        }
    }

    private fun placeMarketOrder(
        companyId: Int,
        orderType: String
    ) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = orderRepository.placeOrder(
                OrderRequest(
                    price = state.value.price,
                    quantity = state.value.quantity,
                    companyId = companyId,
                    orderType = orderType
                )
            )
            orderChannel.send(result)
            updateState { copy(isLoading = false) }
        }
    }

    private fun placeLimitOrder(
        companyId: Int,
        orderType: String
    ) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = orderRepository.placeOrder(
                OrderRequest(
                    price = state.value.limitPrice,
                    quantity = state.value.quantity,
                    companyId = companyId,
                    orderType = orderType
                )
            )
            orderChannel.send(result)
            updateState { copy(isLoading = false) }
        }
    }

    private suspend fun fetchPrice(companyId: Int) {
        val result = orderRepository.fetchLatestStockPrice(companyId)
        if (result is OrderResponseHandler.Success) {
            result.data?.let {
                updateState { copy(price = it.price) }
            } ?: run {
                updateState { copy(price = 100.0) }
            }
        } else {
            // Handle error if necessary
            updateState { copy(isError = true) }
        }
    }

    private fun updateState(update: BuyerScreenState.() -> BuyerScreenState) {
        state.value = state.value.update()
    }
}
