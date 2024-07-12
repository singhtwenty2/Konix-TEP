package com.singhtwenty2.konix.feature_profile.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.singhtwenty2.konix.feature_profile.data.remote.ProfileRemoteService
import com.singhtwenty2.konix.feature_profile.domain.model.DematAccountResponse
import com.singhtwenty2.konix.feature_profile.domain.model.KYCDetailsResponse
import com.singhtwenty2.konix.feature_profile.domain.model.UserDetailResponse
import com.singhtwenty2.konix.feature_profile.domain.repository.ProfileRepository
import com.singhtwenty2.konix.feature_profile.util.ProfileResponseHandler
import com.singhtwenty2.konix.feature_profile.util.handleApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val service: ProfileRemoteService,
    private val pref: SharedPreferences
) : ProfileRepository {
    override suspend fun getUserDetails(): ProfileResponseHandler<UserDetailResponse> {
        return handleApiCall {
            val response = service.getUserDetail("Bearer ${pref.getString("jwt", "")}")
            UserDetailResponse(
                name = response.name,
                email = response.email,
                gender = response.gender,
                age = response.age
            )
        }
    }

    override suspend fun getKYCDetails(): ProfileResponseHandler<KYCDetailsResponse> {
        return handleApiCall {
            val response = service.getKycDetails("Bearer ${pref.getString("jwt", "")}")
            KYCDetailsResponse(
                phoneNumber = response.phoneNumber,
                address = response.address,
                aadharNumber = response.aadharNumber,
                panNumber = response.panNumber,
                employmentStatus = response.employmentStatus,
                investmentExperience = response.investmentExperience,
                riskTolerance = response.riskTolerance,
                annualIncome = response.annualIncome
            )
        }
    }

    override suspend fun getDematAccountDetails(): ProfileResponseHandler<DematAccountResponse> {
        return handleApiCall {
            val response = service.getDematAccountDetails("Bearer ${pref.getString("jwt", "")}")
            DematAccountResponse(
                dematAccountId = response.dematAccountId,
                accountNumber = response.accountNumber,
                accountHolderName = response.accountHolderName,
                address = response.address,
                panNumber = response.panNumber,
                phoneNumber = response.phoneNumber,
                nominee = response.nominee,
                nomineeRelation = response.nomineeRelation,
                openingDate = response.openingDate,
                accountStatus = response.accountStatus,
                brokerName = response.brokerName,
                balance = response.balance
            )
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            val token = pref.getString("jwt", "")
            if (!token.isNullOrEmpty()) {
                val response = service.logout("Bearer $token")
                if (response.isSuccessful) {
                    Log.d("TokenDelete", "Before Token = $token")
                    pref.edit().clear().apply()
                    Log.d("TokenDelete", "After Token = ${pref.getString("jwt", "")}")
                } else {
                    // Handle the logout API failure if necessary TODO
                }
            } else {
                // Handle the case where the token is not available in SharedPreferences TODO
            }
        }
    }
}