package com.singhtwenty2.konix.feature_auth.data.remote

import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.DematAccountCreationRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.KycRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.LoginRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.SignupRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.VerifyOtpRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.DematAccountResponseDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.KYCDetailsResponseDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.LoginResponseDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.UserDetailsResponseDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.response.UserStatusResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("/api/v1/auth/status")
    suspend fun getUserStatus(
        @Header("Authorization") token: String
    ): Response<UserStatusResponseDTO>

    @GET("/api/v1/auth/about")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): Response<UserDetailsResponseDTO>

    @GET("/api/v1/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<Unit>

    @POST("/api/v1/kyc")
    suspend fun performKyc(
        @Header("Authorization") token: String,
        @Body request: KycRequestDTO
    ): Response<Unit>

    @GET("/api/v1/kyc")
    suspend fun getKycDetails(
        @Header("Authorization") token: String
    ): Response<KYCDetailsResponseDTO>

    @POST("/api/v1/demat")
    suspend fun performDematAccountCreation(
        @Header("Authorization") token: String,
        @Body request: DematAccountCreationRequestDTO
    ): Response<Unit>

    @GET("/api/v1/demat")
    suspend fun getDematAccountDetails(
        @Header("Authorization") token: String
    ): Response<DematAccountResponseDTO>
}