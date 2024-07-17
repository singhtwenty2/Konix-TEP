package com.singhtwenty2.konix.feature_portfolio.data.mapper

import com.singhtwenty2.konix.feature_portfolio.data.remote.dto.AllCompaniesInPortfolioResponseDTO
import com.singhtwenty2.konix.feature_portfolio.domain.model.AllCompaniesInPortfolioResponse

fun AllCompaniesInPortfolioResponseDTO.toAllCompaniesInPortfolioResponse()
        : AllCompaniesInPortfolioResponse {
    return AllCompaniesInPortfolioResponse(
        companyId = companyId.toString(),
        companyName = companyName,
        quantity = quantity.toString()
    )
}
