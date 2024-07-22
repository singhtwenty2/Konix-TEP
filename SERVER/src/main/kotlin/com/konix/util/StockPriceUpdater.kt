package com.konix.util

import com.konix.data.repository.entity.Companies
import com.konix.data.repository.entity.StockPrices
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import kotlin.random.Random

data class StockTrend(var basePrice: BigDecimal, var trendDirection: Int)

val stockTrends = mutableMapOf<Int, StockTrend>()

fun stockPriceUpdater() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        while (isActive) {
            transaction {
                Companies.selectAll().forEach { resultRow ->
                    val companyId = resultRow[Companies.companyId]
                    val currentTrend = stockTrends.getOrPut(companyId) {
                        StockTrend(
                            basePrice = BigDecimal(Random.nextDouble(100.0, 1500.0)).setScale(2, RoundingMode.HALF_EVEN),
                            trendDirection = if (Random.nextBoolean()) 1 else -1
                        )
                    }

                    // Adjust base price periodically
                    if (Random.nextDouble() < 0.1) { // 10% chance to change trend direction
                        currentTrend.trendDirection *= -1
                    }
                    currentTrend.basePrice = currentTrend.basePrice.add(
                        BigDecimal(currentTrend.trendDirection * Random.nextDouble(1.0, 5.0)).setScale(2, RoundingMode.HALF_EVEN)
                    )

                    // Apply random fluctuation
                    val fluctuation = BigDecimal(Random.nextDouble(-2.0, 2.0)).setScale(2, RoundingMode.HALF_EVEN)
                    val newPrice = currentTrend.basePrice.add(fluctuation).setScale(2, RoundingMode.HALF_EVEN)

                    // Ensure the new price is not negative
                    val validPrice = if (newPrice < BigDecimal.ZERO) BigDecimal.ZERO else newPrice

                    StockPrices.insert {
                        it[StockPrices.companyId] = companyId
                        it[timestamp] = LocalDateTime.now().toString()
                        it[price] = validPrice
                    }
                }
            }
            delay(900)
        }
    }
}