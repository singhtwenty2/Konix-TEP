package com.singhtwenty2.konix.feature_profile.domain.repository

import com.singhtwenty2.konix.feature_profile.domain.model.DematAccountResponse
import com.singhtwenty2.konix.feature_profile.domain.model.KYCDetailsResponse
import com.singhtwenty2.konix.feature_profile.domain.model.UserDetailResponse
import com.singhtwenty2.konix.feature_profile.util.ProfileResponseHandler

interface ProfileRepository {

    suspend fun getUserDetails(): ProfileResponseHandler<UserDetailResponse>

    suspend fun getKYCDetails(): ProfileResponseHandler<KYCDetailsResponse>

    suspend fun getDematAccountDetails(): ProfileResponseHandler<DematAccountResponse>

    suspend fun logout()
}