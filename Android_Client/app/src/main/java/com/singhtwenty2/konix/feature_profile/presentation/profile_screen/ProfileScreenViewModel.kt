package com.singhtwenty2.konix.feature_profile.presentation.profile_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singhtwenty2.konix.feature_profile.domain.repository.ProfileRepository
import com.singhtwenty2.konix.feature_profile.util.ProfileResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var state = mutableStateOf(ProfileScreenState())
        private set

    init {
        fetchUserDetail()
        fetchKYCDetail()
        fetchDematAccountDetail()
    }

    private fun fetchUserDetail() {
        viewModelScope.launch {
            updateLoadingState(isUserDetailLoading = true)
            when (val result = profileRepository.getUserDetails()) {
                is ProfileResponseHandler.Success -> {
                    state.value = state.value.copy(userDetail = result.data)
                }

                is ProfileResponseHandler.BadRequest -> {
                    updateErrorState("Failed to fetch user detail: Bad Request")
                }

                is ProfileResponseHandler.UnAuthorized -> {
                    updateErrorState("Failed to fetch user detail: Unauthorized")
                }

                is ProfileResponseHandler.InternalServerError -> {
                    updateErrorState("Failed to fetch user detail: Server Error")
                }

                is ProfileResponseHandler.UnknownError -> {
                    updateErrorState(result.message ?: "Failed to fetch user detail: Unknown Error")
                }
            }
            updateLoadingState(isUserDetailLoading = false)
        }
    }

    private fun fetchKYCDetail() {
        viewModelScope.launch {
            updateLoadingState(isKYCDetailLoading = true)
            when (val result = profileRepository.getKYCDetails()) {
                is ProfileResponseHandler.Success -> {
                    state.value = state.value.copy(kycDetail = result.data)
                }

                is ProfileResponseHandler.BadRequest -> {
                    updateErrorState("Failed to fetch KYC detail: Bad Request")
                }

                is ProfileResponseHandler.UnAuthorized -> {
                    updateErrorState("Failed to fetch KYC detail: Unauthorized")
                }

                is ProfileResponseHandler.InternalServerError -> {
                    updateErrorState("Failed to fetch KYC detail: Server Error")
                }

                is ProfileResponseHandler.UnknownError -> {
                    updateErrorState(result.message ?: "Failed to fetch KYC detail: Unknown Error")
                }
            }
            updateLoadingState(isKYCDetailLoading = false)
        }
    }

    private fun fetchDematAccountDetail() {
        viewModelScope.launch {
            updateLoadingState(isDematAccountDetailLoading = true)
            when (val result = profileRepository.getDematAccountDetails()) {
                is ProfileResponseHandler.Success -> {
                    state.value = state.value.copy(dematAccountDetail = result.data)
                }

                is ProfileResponseHandler.BadRequest -> {
                    updateErrorState("Failed to fetch demat account detail: Bad Request")
                }

                is ProfileResponseHandler.UnAuthorized -> {
                    updateErrorState("Failed to fetch demat account detail: Unauthorized")
                }

                is ProfileResponseHandler.InternalServerError -> {
                    updateErrorState("Failed to fetch demat account detail: Server Error")
                }

                is ProfileResponseHandler.UnknownError -> {
                    updateErrorState(
                        result.message ?: "Failed to fetch demat account detail: Unknown Error"
                    )
                }
            }
            updateLoadingState(isDematAccountDetailLoading = false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            profileRepository.logout()
        }
    }

    private fun updateLoadingState(
        isUserDetailLoading: Boolean = state.value.isUserDetailLoading,
        isKYCDetailLoading: Boolean = state.value.isKYCDetailLoading,
        isDematAccountDetailLoading: Boolean = state.value.isDematAccountDetailLoading
    ) {
        state.value = state.value.copy(
            isUserDetailLoading = isUserDetailLoading,
            isKYCDetailLoading = isKYCDetailLoading,
            isDematAccountDetailLoading = isDematAccountDetailLoading
        )
    }

    private fun updateErrorState(errorMessage: String) {
        state.value = state.value.copy(error = errorMessage)
    }
}
