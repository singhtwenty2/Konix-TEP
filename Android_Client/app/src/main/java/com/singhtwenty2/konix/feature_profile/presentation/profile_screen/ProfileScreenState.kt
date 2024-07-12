package com.singhtwenty2.konix.feature_profile.presentation.profile_screen

import com.singhtwenty2.konix.feature_profile.domain.model.DematAccountResponse
import com.singhtwenty2.konix.feature_profile.domain.model.KYCDetailsResponse
import com.singhtwenty2.konix.feature_profile.domain.model.UserDetailResponse

data class ProfileScreenState(
    val userDetail: UserDetailResponse? = null,
    val kycDetail: KYCDetailsResponse? = null,
    val dematAccountDetail: DematAccountResponse? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isUserDetailLoading: Boolean = false,
    val isKYCDetailLoading: Boolean = false,
    val isDematAccountDetailLoading: Boolean = false,
)
