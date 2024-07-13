package com.konix.data.repository.dao

import com.konix.data.dto.response.PortfolioResponseDTO
import com.konix.data.repository.entity.Portfolios
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object PortfolioDAO {

    fun addStockToPortfolio(userId: Int, companyId: Int, quantity: Int) {
        transaction {
            Portfolios.insert {
                it[Portfolios.userId] = userId
                it[Portfolios.companyId] = companyId
                it[Portfolios.quantity] = quantity
            }
        }
    }

    fun getStockQuantity(userId: Int, companyId: Int): Int {
        return transaction {
            Portfolios.select { (Portfolios.userId eq userId) and (Portfolios.companyId eq companyId) }
                .singleOrNull()
                ?.let { it[Portfolios.quantity] } ?: 10
        }
    }

    fun updateStockQuantity(userId: Int, companyId: Int, quantity: Int) {
        transaction {
            Portfolios.update({ (Portfolios.userId eq userId) and (Portfolios.companyId eq companyId) }) {
                it[Portfolios.quantity] = quantity
            }
        }
    }

    fun getUserPortfolio(userId: Int): List<PortfolioResponseDTO> {
        return transaction {
            Portfolios.select { Portfolios.userId eq userId }
                .map {
                    val companyId = it[Portfolios.companyId]
                    val quantity = it[Portfolios.quantity]
                    val companyDetails = CompanyDAO.getCompany(companyId)
                    PortfolioResponseDTO(companyId, companyDetails?.name ?: "Unknown", quantity)
                }
        }
    }
}