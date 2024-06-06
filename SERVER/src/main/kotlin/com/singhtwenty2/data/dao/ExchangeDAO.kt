package com.singhtwenty2.data.dao

import com.singhtwenty2.data.entity.Exchanges
import com.singhtwenty2.data.request.ExchangeRequestDTO
import com.singhtwenty2.data.response.ExchangeResponseDTO
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object ExchangeDAO {

    fun insertExchange(requestDTO: ExchangeRequestDTO) {
        return transaction {
            Exchanges.insert {
                it[name] = requestDTO.name
                it[description] = requestDTO.description
                it[location] = requestDTO.location
                it[website] = requestDTO.website
                it[timezone] = requestDTO.timeZone
                it[closingHours] = requestDTO.closingHours
                it[openingHours] = requestDTO.openingHours
                it[currency] = requestDTO.currency
                it[establishedDate] = requestDTO.establishedDate
                it[symbol] = requestDTO.symbol
            }
        }
    }

    fun getExchanges(id: Int): ExchangeResponseDTO? {
        return transaction {
            Exchanges.select { Exchanges.exchangeId eq id }
                .singleOrNull()?.let {
                    ExchangeResponseDTO(
                        id = it[Exchanges.exchangeId],
                        name = it[Exchanges.name],
                        description = it[Exchanges.description],
                        location = it[Exchanges.location],
                        openingHours = it[Exchanges.openingHours],
                        closingHours = it[Exchanges.closingHours],
                        currency = it[Exchanges.currency],
                        establishedDate = it[Exchanges.establishedDate],
                        website = it[Exchanges.website],
                        timeZone = it[Exchanges.timezone],
                        symbol = it[Exchanges.symbol]
                    )
                }
        }
    }
}