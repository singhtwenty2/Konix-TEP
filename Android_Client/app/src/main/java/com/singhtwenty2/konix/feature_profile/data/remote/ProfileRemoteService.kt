package com.singhtwenty2.konix.feature_profile.data.remote

import com.singhtwenty2.konix.feature_profile.data.remote.dto.DematAccountResponseDTO
import com.singhtwenty2.konix.feature_profile.data.remote.dto.KYCDetailsResponseDTO
import com.singhtwenty2.konix.feature_profile.data.remote.dto.UserDetailResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileRemoteService {

    @GET("/api/v1/auth/about")
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
    ): UserDetailResponseDTO

    @GET("/api/v1/kyc")
    suspend fun getKycDetails(
        @Header("Authorization") token: String
    ): KYCDetailsResponseDTO

    @GET("/api/v1/demat")
    suspend fun getDematAccountDetails(
        @Header("Authorization") token: String
    ): DematAccountResponseDTO

    @GET("/api/v1/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<Unit>

}
