package com.singhtwenty2.util

import com.singhtwenty2.data.repository.entity.Companies
import com.singhtwenty2.data.repository.entity.StockPrices
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import kotlin.random.Random

fun insertDummyStockPrices() {
    return transaction {
        // Total number of companies
        val companies = Companies.selectAll().map {
            it[Companies.companyId]
        }
        val now = LocalDateTime.now()
        companies.forEach { companyId ->
            // Inserting dummy prices for the past 30 days
            for (i in 30 downTo 0) {
                val date = now.minusDays(i.toLong())
                val price = BigDecimal(Random.nextDouble(100.0, 1500.0)).setScale(2, RoundingMode.HALF_EVEN)
                StockPrices.insert {
                    it[StockPrices.companyId] = companyId
                    it[timestamp] = date.toString()
                    it[StockPrices.price] = price
                }
            }
        }
    }
}