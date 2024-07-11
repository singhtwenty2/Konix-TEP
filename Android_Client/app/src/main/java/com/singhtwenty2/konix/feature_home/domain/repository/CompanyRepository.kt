package com.singhtwenty2.konix.feature_home.domain.repository

import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing
import com.singhtwenty2.konix.feature_home.domain.model.ExchangeDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.model.FullCompanyDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler

interface CompanyRepository {

    suspend fun getPaginatedCompanies(
        page: Int,
        size: Int
    ): CompanyResponseHandler<List<CompanyListing>>

    suspend fun getCompanyDetailsById(
        id: Int
    ): CompanyResponseHandler<FullCompanyDetailsResponse>

    suspend fun getDetailsAboutExchange(
        id: Int
    ): CompanyResponseHandler<ExchangeDetailsResponse>

    suspend fun getStockPriceByCompanyId(
        companyId: Int
    ): CompanyResponseHandler<List<StockPriceResponse>>

    suspend fun getHistoricalStockPriceByCompanyId(
        companyId: Int
    ): CompanyResponseHandler<List<StockPriceResponse>>
}