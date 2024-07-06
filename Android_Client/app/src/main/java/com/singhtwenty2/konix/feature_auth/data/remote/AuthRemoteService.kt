package com.singhtwenty2.konix.feature_auth.data.remote

import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.LoginRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.SignupRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.VerifyOtpRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.LoginResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteService {

    @POST("/api/v1/auth/signup")
    suspend fun signup(
        @Body request: SignupRequestDTO
    )

    @POST("/api/v1/auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequestDTO
    )

    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequestDTO
    ): LoginResponseDTO
}