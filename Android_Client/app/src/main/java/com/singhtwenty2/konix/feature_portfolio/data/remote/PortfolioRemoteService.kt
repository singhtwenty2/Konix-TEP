package com.singhtwenty2.konix.feature_portfolio.data.remote

import com.singhtwenty2.konix.feature_portfolio.data.remote.dto.AllCompaniesInPortfolioResponseDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PortfolioRemoteService {

    @GET("/api/v1/portfolio")
    suspend fun getAllCompaniesInPortfolio(
        @Header("Authorization") token: String
    ): List<AllCompaniesInPortfolioResponseDTO>

    @GET("/api/v1/portfolio/{companyId}")
    suspend fun getStockQuantity(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: Int
    ): Int
}