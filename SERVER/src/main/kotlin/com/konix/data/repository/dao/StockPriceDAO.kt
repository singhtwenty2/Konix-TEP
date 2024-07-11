package com.konix.data.repository.dao

import com.konix.data.dto.response.LatestStockPriceResponseDTO
import com.konix.data.repository.entity.StockPrices
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object StockPriceDAO {

    fun getLatestStockPriceByCompany(companyId: Int): LatestStockPriceResponseDTO? {
        return transaction {
            val result = StockPrices.select { StockPrices.companyId eq companyId }
                .orderBy(StockPrices.timestamp to SortOrder.DESC)
                .limit(1)
                .map {
                    LatestStockPriceResponseDTO(
                        price = it[StockPrices.price].toDouble(),
                    )
                }
                .firstOrNull()
            result ?: throw NoSuchElementException("No stock price found for company ID $companyId")
        }
    }
}