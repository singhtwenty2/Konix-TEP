package com.konix.data.repository.dao

import com.konix.data.dto.request.TransactionRequestDTO
import com.konix.data.dto.response.TransactionResponsetDTO
import com.konix.data.repository.entity.Transactions
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object TransactionDAO {

    fun createTransaction(transactionRequestDTO: TransactionRequestDTO) {
        return transaction {
            Transactions.insert {
                it[orderId] = transactionRequestDTO.orderId
                it[userId] = transactionRequestDTO.userId
                it[companyId] = transactionRequestDTO.companyId
                it[quantity] = transactionRequestDTO.quantity
                it[price] = transactionRequestDTO.price.toBigDecimal()
                it[timestamp] = System.currentTimeMillis().toString()
            }
        }
    }

    fun getTransactionsByUser(userId: Int): List<TransactionResponsetDTO?> {
        return transaction {
            Transactions.select { Transactions.userId eq userId }.map {
                TransactionResponsetDTO(
                    transactionId = it[Transactions.id],
                    userId = it[Transactions.userId],
                    companyId = it[Transactions.companyId],
                    orderId = it[Transactions.orderId],
                    price = it[Transactions.price].toDouble(),
                    quantity = it[Transactions.quantity],
                    timeStamp = it[Transactions.timestamp]
                )
            }
        }
    }
}