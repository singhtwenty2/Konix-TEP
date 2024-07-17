package com.singhtwenty2.konix.feature_portfolio.presentation.screen.portfolio_screen

import com.singhtwenty2.konix.feature_portfolio.domain.model.AllCompaniesInPortfolioResponse

data class PortfolioScreenState(
    val portfolio: List<AllCompaniesInPortfolioResponse> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
