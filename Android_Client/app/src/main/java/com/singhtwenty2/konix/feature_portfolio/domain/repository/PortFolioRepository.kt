package com.singhtwenty2.konix.feature_portfolio.domain.repository

import com.singhtwenty2.konix.feature_portfolio.domain.model.AllCompaniesInPortfolioResponse
import com.singhtwenty2.konix.feature_portfolio.util.PortfolioResponseHandler

interface PortFolioRepository {

    suspend fun getAllCompaniesInPortfolio()
            : PortfolioResponseHandler<
            List<
                    AllCompaniesInPortfolioResponse
                    >>

    suspend fun getStockQuantity(companyId: Int)
            : PortfolioResponseHandler<Int>
}