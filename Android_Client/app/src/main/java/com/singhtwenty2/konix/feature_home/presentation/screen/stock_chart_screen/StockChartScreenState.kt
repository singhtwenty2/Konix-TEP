package com.singhtwenty2.konix.feature_home.presentation.screen.stock_chart_screen

import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse

data class StockChartScreenState(
    val stockPrices: List<StockPriceResponse>? = null,
    val historicalStockPrices: List<StockPriceResponse>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCumulativePriceFetch: Boolean = false,
    val isRefreshing: Boolean = false
)
