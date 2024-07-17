package com.singhtwenty2.konix.feature_portfolio.data.repository

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_portfolio.data.mapper.toAllCompaniesInPortfolioResponse
import com.singhtwenty2.konix.feature_portfolio.data.remote.PortfolioRemoteService
import com.singhtwenty2.konix.feature_portfolio.domain.model.AllCompaniesInPortfolioResponse
import com.singhtwenty2.konix.feature_portfolio.domain.repository.PortFolioRepository
import com.singhtwenty2.konix.feature_portfolio.util.PortfolioResponseHandler
import com.singhtwenty2.konix.feature_portfolio.util.handleApiCall

class PortfolioRepositoryImpl(
    private val service: PortfolioRemoteService,
    private val pref: SharedPreferences
) : PortFolioRepository {
    override suspend fun getAllCompaniesInPortfolio(): PortfolioResponseHandler<List<AllCompaniesInPortfolioResponse>> {
        return handleApiCall {
            val response = service.getAllCompaniesInPortfolio(
                token = "Bearer ${pref.getString("jwt", "")}"
            )
            response.map { dto ->
                dto.toAllCompaniesInPortfolioResponse()
            }
        }
    }

    override suspend fun getStockQuantity(companyId: Int): PortfolioResponseHandler<Int> {
        return handleApiCall {
            val response = service.getStockQuantity(
                token = "Bearer ${pref.getString("jwt", "")}",
                companyId = companyId
            )
            response
        }
    }
}