package com.singhtwenty2.konix.feature_home.presentation.screen.home_screen

import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing
import com.singhtwenty2.konix.feature_home.domain.model.FullCompanyDetailsResponse

data class HomeScreenState(
    val searchQuery: String = "",
    val companyList: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isBottomSheetOpen: Boolean = false,
    val companyDetails: FullCompanyDetailsResponse? = null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
