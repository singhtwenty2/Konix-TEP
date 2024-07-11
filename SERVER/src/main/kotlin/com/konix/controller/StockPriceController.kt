package com.konix.controller

import com.konix.data.repository.entity.StockPrices
import com.konix.data.dto.response.StockPriceResponseDTO
import com.konix.data.repository.dao.StockPriceDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SortOrder
import java.sql.Timestamp
import java.time.format.DateTimeFormatter

fun Route.stockPrice() {
    authenticate {
        get("/api/v1/stockprice-realtime") {
            val companyId = call.request.queryParameters["companyId"]?.toIntOrNull()
            companyId?.let {
                val stockPrices = transaction {
                    StockPrices.select {
                        StockPrices.companyId eq companyId
                    }
                        .orderBy(StockPrices.timestamp to SortOrder.DESC)
                        .limit(30)
                        .map {
                            val timestamp = it[StockPrices.timestamp] as Timestamp
                            StockPriceResponseDTO(
                                timeStamp = timestamp.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                price = it[StockPrices.price].toDouble()
                            )
                        }
                }
                call.respond(stockPrices)
            } ?: call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        get("/api/v1/stockprice-historical") {
            val companyId = call.request.queryParameters["companyId"]?.toIntOrNull()
            companyId?.let {
                val stockPrices = transaction {
                    StockPrices.select {
                        StockPrices.companyId eq companyId
                    }
                        .orderBy(StockPrices.timestamp to SortOrder.ASC)
                        .limit(500)
                        .map {
                            val timestamp = it[StockPrices.timestamp] as Timestamp
                            StockPriceResponseDTO(
                                timeStamp = timestamp.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                price = it[StockPrices.price].toDouble()
                            )
                        }
                }
                call.respond(stockPrices)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
        get("/api/v1/stockprice") {
            val companyId = call.request.queryParameters["companyId"]?.toIntOrNull()
            companyId?.let {
                val latestPrice = StockPriceDAO.getLatestStockPriceByCompany(companyId)
                latestPrice?.let {
                    call.respond(latestPrice)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}
