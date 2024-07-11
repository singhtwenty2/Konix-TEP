package com.singhtwenty2.konix.feature_home.presentation.screen.exchange_screen

import com.singhtwenty2.konix.feature_home.domain.model.ExchangeDetailsResponse

data class ExchangeScreenState(
    val exchangeDetail: ExchangeDetailsResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
