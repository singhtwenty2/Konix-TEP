package com.konix.data.repository.dao

import com.konix.data.dto.request.CompanyExchangeRequestDTO
import com.konix.data.dto.response.CompanyExchangeResponseDTO
import com.konix.data.repository.entity.CompanyExchanges
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object CompanyExchangeDAO {

    fun insertCompanyExchange(requestDTO: CompanyExchangeRequestDTO) {
        return transaction {
            CompanyExchanges.insert {
                it[companyId] = requestDTO.companyId
                it[exchangeId] = requestDTO.exchangeId
            }
        }
    }

    fun getCompanyExchange(companyId: Int): List<CompanyExchangeResponseDTO> {
        return transaction {
            CompanyExchanges.select {
                CompanyExchanges.companyId eq companyId
            }.map {
                CompanyExchangeResponseDTO(
                    id = it[CompanyExchanges.id],
                    companyId = it[CompanyExchanges.companyId],
                    exchangeId = it[CompanyExchanges.exchangeId]
                )
            }
        }
    }
}