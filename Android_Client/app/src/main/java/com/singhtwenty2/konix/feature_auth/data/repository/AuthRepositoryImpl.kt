package com.singhtwenty2.konix.feature_auth.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.singhtwenty2.konix.core.util.handleApiCall
import com.singhtwenty2.konix.feature_auth.data.mapper.toDematAccountCreationRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toKycRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toLoginRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toSignupRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toVerifyOtpRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.AuthRemoteService
import com.singhtwenty2.konix.feature_auth.domain.model.DematAccountCreationRequest
import com.singhtwenty2.konix.feature_auth.domain.model.KycRequest
import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
import com.singhtwenty2.konix.feature_auth.domain.model.UserDetailsResponse
import com.singhtwenty2.konix.feature_auth.domain.model.UserStatusResponse
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

class AuthRepositoryImpl(
    private val service: AuthRemoteService,
    private val pref: SharedPreferences
) : AuthRepository {

    override suspend fun signup(signupRequest: SignupRequest): AuthResponseHandler<Unit> {
        return handleApiCall { service.signup(signupRequest.toSignupRequestDTO()) }
    }

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): AuthResponseHandler<Unit> {
        return handleApiCall { service.verifyOtp(verifyOtpRequest.toVerifyOtpRequestDTO()) }
    }

    override suspend fun login(loginRequest: LoginRequest): AuthResponseHandler<Unit> {
        return handleApiCall {
            val response = service.login(loginRequest.toLoginRequestDTO())
            pref.edit()
                .putString("jwt", response.token)
                .apply()
            Log.d("JWT", "login: ${response.token}")
        }
    }

    override suspend fun getUserStatus(): AuthResponseHandler<UserStatusResponse> {
        return handleApiCall {
            val response = service.getUserStatus("Bearer ${pref.getString("jwt", "")}")
            response.body()?.let {
                UserStatusResponse(
                    it.isKycDone,
                    it.isDematCreated
                )
            }!!
        }
    }

    override suspend fun getUserDetails(): AuthResponseHandler<UserDetailsResponse> {
        return handleApiCall {
            val response = service.getUserDetails("Bearer ${pref.getString("jwt", "")}")
            response.body()?.let {
                UserDetailsResponse(
                    it.name,
                    it.email,
                    it.age,
                    it.gender
                )
            }!!
        }

    }

    override suspend fun performKyc(kycRequest: KycRequest): AuthResponseHandler<Unit> {
        return handleApiCall {
            service.performKyc(
                token = "Bearer ${pref.getString("jwt", "")}",
                request = kycRequest.toKycRequestDTO()
            )
        }
    }

    override suspend fun performDematCreation(dematAccountCreationRequest: DematAccountCreationRequest): AuthResponseHandler<Unit> {
        return handleApiCall {
            service.performDematAccountCreation(
                token = "Bearer ${pref.getString("jwt", "")}",
                request = dematAccountCreationRequest.toDematAccountCreationRequestDTO()
            )
        }
    }

    override suspend fun logout(): AuthResponseHandler<Unit> {
        TODO("Not yet implemented")
    }
}