package com.singhtwenty2.konix.feature_home.presentation.screen.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing
import com.singhtwenty2.konix.feature_home.domain.model.ExchangeDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.model.FullCompanyDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.repository.CompanyRepository
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    var state = mutableStateOf(HomeScreenState())

    private val companyListingResultChannel =
        Channel<CompanyResponseHandler<List<CompanyListing>>>()
    val companyListingResult = companyListingResultChannel.receiveAsFlow()

    private val companyInfoResultChannel = Channel<
            CompanyResponseHandler<
                    FullCompanyDetailsResponse
                    >>()
    val companyInfoResult = companyInfoResultChannel.receiveAsFlow()

    private val exchangeDetailsResultChannel = Channel<
            CompanyResponseHandler<
                    ExchangeDetailsResponse
                    >>()
    val exchangeDetailsResult = exchangeDetailsResultChannel.receiveAsFlow()

    init {
        getCompanies()
    }

    fun onEvent(event: HomeScreenUiEvents) {
        when (event) {
            is HomeScreenUiEvents.SearchQueryChanged -> {
                state.value = state.value.copy(searchQuery = event.query)
            }

            is HomeScreenUiEvents.CompanyClicked -> {
                setSelectedCompany(event.id)
                getCompanyDetails(event.id)
            }

            HomeScreenUiEvents.RefreshCompanies -> getCompanies()
        }
    }

    private fun getCompanies() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = companyRepository.getPaginatedCompanies(1, 35)
            companyListingResultChannel.send(result)
            state.value = state.value.copy(isLoading = false)
        }
    }

    private fun getCompanyDetails(id: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val result = companyRepository.getCompanyDetailsById(id)
            companyInfoResultChannel.send(result)
            when (result) {
                is CompanyResponseHandler.Success -> {
                    state.value = state.value.copy(
                        companyDetails = result.data,
                        isLoading = false
                    )
                }
                else -> {
                    state.value = state.value.copy(isLoading = false)
                }
            }
        }
    }

    fun setSelectedCompany(id: Int) {
        val company = state.value.companyList.find { it.id == id }
        state.value = state.value.copy(selectedCompany = company)
    }
}
