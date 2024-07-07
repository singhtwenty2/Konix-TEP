package com.singhtwenty2.konix.feature_auth.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.singhtwenty2.konix.feature_auth.data.mapper.toLoginRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toSignupRequestDTO
import com.singhtwenty2.konix.feature_auth.data.mapper.toVerifyOtpRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.AuthRemoteService
import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler
import com.singhtwenty2.konix.feature_auth.util.handleApiCall

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

}