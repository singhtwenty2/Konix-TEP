package com.singhtwenty2.app_routing

import com.singhtwenty2.data.entity.StockPrices
import com.singhtwenty2.data.response.StockPriceResponseDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.format.DateTimeFormatter

fun Route.stockPrice() {
    authenticate {
        get("api/v1/stockprice") {
            val companyId = call.request.queryParameters["companyId"]?.toIntOrNull()
            companyId?.let {
                val stockPrices = transaction {
                    StockPrices.select {
                        StockPrices.companyId eq companyId
                    }.map {
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
    }
}