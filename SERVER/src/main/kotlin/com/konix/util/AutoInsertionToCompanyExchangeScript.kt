package com.konix.util

import com.konix.data.repository.dao.CompanyExchangeDAO
import com.konix.data.dto.request.CompanyExchangeRequestDTO
import io.ktor.server.application.*

fun Application.autoInsertionToCompanyExchangeScript() {
    val exchanges = listOf(1, 2)
    val companies = (1..4).toList()

    companies.forEach { companyId->
        exchanges.forEach { exchangeId->
            val requestDTO = CompanyExchangeRequestDTO(
                companyId = companyId,
                exchangeId = exchangeId
            )
            CompanyExchangeDAO.insertCompanyExchange(
                requestDTO = requestDTO
            )
        }
    }
}