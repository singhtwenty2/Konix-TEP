package com.singhtwenty2.konix.feature_order_placing.presentation.screen.order_detail_screen

sealed class OrderDetailUiEvent {
    data object RefreshOrder : OrderDetailUiEvent()
}