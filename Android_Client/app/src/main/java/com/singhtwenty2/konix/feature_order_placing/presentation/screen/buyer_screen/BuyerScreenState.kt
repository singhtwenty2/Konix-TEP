package com.singhtwenty2.konix.feature_order_placing.presentation.screen.buyer_screen

data class BuyerScreenState(
    var price: Double = 0.0,
    val quantity: Int = 1,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val limitPrice: Double = 100.0,
    val isLimitOrder: Boolean = false,
)
