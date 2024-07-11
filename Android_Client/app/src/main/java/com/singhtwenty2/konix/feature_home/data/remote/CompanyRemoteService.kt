package com.singhtwenty2.konix.feature_home.data.remote

import com.singhtwenty2.konix.feature_home.data.remote.dto.ExchangeDetailsResponseDTO
import com.singhtwenty2.konix.feature_home.data.remote.dto.FullCompanyDetailsResponseDTO
import com.singhtwenty2.konix.feature_home.data.remote.dto.PaginatedCompanyResponseDTO
import com.singhtwenty2.konix.feature_home.data.remote.dto.StockPriceResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyRemoteService {

    @GET("/api/v1/companies")
    suspend fun getPaginatedCompanies(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): Response<PaginatedCompanyResponseDTO>

    @GET("/api/v1/company/{id}")
    suspend fun getCompanyDetailsById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<FullCompanyDetailsResponseDTO>

    @GET("/api/v1/exchanges/{id}")
    suspend fun getDeatilsAboutExchange(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<ExchangeDetailsResponseDTO>

    @GET("/api/v1/stockprice-realtime")
    suspend fun getStockPriceByCompanyId(
        @Query("companyId") companyId: Int,
        @Header("Authorization") token: String
    ): Response<List<StockPriceResponseDTO>>

    @GET("/api/v1/stockprice-historical")
    suspend fun getHistoricalStockPriceByCompanyId(
        @Query("companyId") companyId: Int,
        @Header("Authorization") token: String
    ): Response<List<StockPriceResponseDTO>>
}