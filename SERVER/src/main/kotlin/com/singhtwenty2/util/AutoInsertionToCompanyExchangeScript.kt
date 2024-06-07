package com.singhtwenty2.util

import com.singhtwenty2.data.dao.CompanyExchangeDAO
import com.singhtwenty2.data.request.CompanyExchangeRequestDTO
import io.ktor.server.application.*

fun Application.autoInsertionToCompanyExchangeScript() {
    val exchanges = listOf(1, 2)
    val companies = (45..50).toList()

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