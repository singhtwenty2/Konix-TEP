package com.singhtwenty2.konix.feature_order_placing.presentation.screen.buyer_screen

sealed class BuyerUiEvent {
    data class QuantityChanged(val quantity: Int) : BuyerUiEvent()
    data class PriceChanged(val price: Double) : BuyerUiEvent()
    data class BuyClicked(
        val companyId: Int,
        val orderType: String
    ) : BuyerUiEvent()
    data class Refreshed(val companyId: Int) : BuyerUiEvent()
    data class LimitPriceChanged(val limitPrice: Double) : BuyerUiEvent()
    data class LimitOrderTriggered(val isLimitOrder: Boolean) : BuyerUiEvent()
    data class LimitOrderPlaced(
        val companyId: Int,
        val orderType: String
    ) : BuyerUiEvent()
}