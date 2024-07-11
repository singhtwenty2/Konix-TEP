package com.singhtwenty2.konix.feature_home.presentation.screen.home_screen

sealed class HomeScreenUiEvents {
    data class SearchQueryChanged(val query: String) : HomeScreenUiEvents()
    data object RefreshCompanies : HomeScreenUiEvents()
    data class CompanyClicked(val id: Int) : HomeScreenUiEvents()
}