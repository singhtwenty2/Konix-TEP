package com.singhtwenty2.konix.feature_portfolio.presentation.screen.portfolio_screen

sealed class PortfolioScreenUiEvent {
    data object OnRefresh: PortfolioScreenUiEvent()
}