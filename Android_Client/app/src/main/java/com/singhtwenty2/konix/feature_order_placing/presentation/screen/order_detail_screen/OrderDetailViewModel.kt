package com.singhtwenty2.konix.feature_order_placing.presentation.screen.order_detail_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse
import com.singhtwenty2.konix.feature_order_placing.domain.repository.OrderRepository
import com.singhtwenty2.konix.feature_order_placing.util.OrderResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    var state = mutableStateOf(OrderDetailState())

    init {
        fetchOrder()
    }

    fun onEvent(event: OrderDetailUiEvent) {
        when(event) {
            is OrderDetailUiEvent.RefreshOrder -> {
                fetchOrder()
            }
        }
    }

    private fun fetchOrder() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            when(val result = orderRepository.fetchOrder()) {
                is OrderResponseHandler.Success -> {
                    state.value = state.value.copy(isError = false)
                    val data = result.data
                    data?.let {
                        state.value = state.value.copy(order = it)
                    }
                }
                is OrderResponseHandler.BadRequest -> {
                    state.value = state.value.copy(isError = true)
                    state.value = state.value.copy(error = "Failed to fetch order: Bad Request")
                }
                is OrderResponseHandler.InternalServerError -> {
                    state.value = state.value.copy(isError = true)
                    state.value = state.value.copy(error = "Failed to fetch order: Server Error")
                }
                is OrderResponseHandler.UnAuthorized -> {
                    state.value = state.value.copy(isError = true)
                    state.value = state.value.copy(error = "Failed to fetch order: Unauthorized")
                }
                is OrderResponseHandler.UnknownError -> {
                    state.value = state.value.copy(isError = true)
                    state.value = state.value.copy(error = result.message ?: "Failed to fetch order: Unknown Error")
                }
            }
            state.value = state.value.copy(isLoading = false)
        }
    }
}