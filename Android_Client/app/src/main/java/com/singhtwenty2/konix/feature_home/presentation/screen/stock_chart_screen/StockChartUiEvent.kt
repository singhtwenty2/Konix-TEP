package com.singhtwenty2.konix.feature_home.presentation.screen.stock_chart_screen

sealed class StockChartUiEvent {
    data class OnRefresh(val companyId: Int) : StockChartUiEvent()
    data class OnCumulativePriceFetch(val isCumulative: Boolean) : StockChartUiEvent()
}