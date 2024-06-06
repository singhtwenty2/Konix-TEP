package com.singhtwenty2.data.dao

import com.singhtwenty2.data.entity.Companies
import com.singhtwenty2.data.request.CompanyRequestDTO
import com.singhtwenty2.data.response.CompanyResponseDTO
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object CompanyDAO {

    fun insertCompany(requestDTO: CompanyRequestDTO) {
        return transaction {
            Companies.insert {
                it[name] = requestDTO.name
                it[symbol] = requestDTO.symbol
                it[sector] = requestDTO.sector
                it[marketCap] = requestDTO.marketCap.toBigDecimal()
                it[ipoDate] = requestDTO.ipoDate
                it[description] = requestDTO.description
                it[website] = requestDTO.website
                it[headquarters] = requestDTO.headquarters
                it[ceo] = requestDTO.ceo
                it[employees] = requestDTO.employees.toInt()
                it[foundedDate] = requestDTO.foundedDate
            }
        }
    }

    fun getCompany(id: Int): CompanyResponseDTO? {
        return transaction {
            Companies.select { Companies.companyId eq id }
                .singleOrNull()?.let {
                    CompanyResponseDTO(
                        id = it[Companies.companyId],
                        name = it[Companies.name],
                        symbol = it[Companies.symbol],
                        sector = it[Companies.sector],
                        description = it[Companies.description],
                        marketCap = it[Companies.marketCap].toDouble(),
                        ipoDate = it[Companies.ipoDate],
                        website = it[Companies.website],
                        ceo = it[Companies.ceo],
                        employees = it[Companies.employees],
                        foundedDate = it[Companies.foundedDate],
                        headquarters = it[Companies.headquarters]
                    )
                }
        }
    }
}