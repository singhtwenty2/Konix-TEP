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

fun stockPriceUpdater() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        while (isActive) {
            transaction {
                Companies.selectAll().forEach { resultRow ->
                    val newPrice = BigDecimal(Random.nextDouble(100.0, 1500.0)).setScale(2, RoundingMode.HALF_EVEN)
                    StockPrices.insert {
                        it[companyId] = resultRow[Companies.companyId]
                        it[timestamp] = LocalDateTime.now().toString()
                        it[price] = newPrice

                    }
                }
            }
            delay(900)
        }
    }
}