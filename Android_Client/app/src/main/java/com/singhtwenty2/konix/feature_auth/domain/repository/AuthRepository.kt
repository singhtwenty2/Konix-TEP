package com.singhtwenty2.konix.feature_auth.domain.repository

import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
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
}