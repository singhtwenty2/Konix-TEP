package com.singhtwenty2.konix.feature_auth.domain.repository

import com.singhtwenty2.konix.feature_auth.domain.model.DematAccountCreationRequest
import com.singhtwenty2.konix.feature_auth.domain.model.KycRequest
import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
import com.singhtwenty2.konix.feature_auth.domain.model.UserDetailsResponse
import com.singhtwenty2.konix.feature_auth.domain.model.UserStatusResponse
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

interface AuthRepository {

    suspend fun signup(
        signupRequest: SignupRequest
    ): AuthResponseHandler<Unit>

    suspend fun verifyOtp(
        verifyOtpRequest: VerifyOtpRequest
    ): AuthResponseHandler<Unit>

    suspend fun login(
        loginRequest: LoginRequest
    ): AuthResponseHandler<Unit>

    suspend fun logout(): AuthResponseHandler<Unit>

    suspend fun getUserStatus(): AuthResponseHandler<UserStatusResponse>

    suspend fun getUserDetails(): AuthResponseHandler<UserDetailsResponse>

    suspend fun performKyc(
        kycRequest: KycRequest
    ): AuthResponseHandler<Unit>

    suspend fun performDematCreation(
        dematAccountCreationRequest: DematAccountCreationRequest
    ): AuthResponseHandler<Unit>
}