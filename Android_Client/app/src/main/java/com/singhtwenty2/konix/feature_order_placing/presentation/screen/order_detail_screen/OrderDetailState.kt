package com.singhtwenty2.konix.feature_order_placing.presentation.screen.order_detail_screen

import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse

data class OrderDetailState(
    val order: List<OrderResponse> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isError: Boolean = false
)
