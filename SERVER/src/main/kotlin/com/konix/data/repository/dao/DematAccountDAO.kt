package com.konix.data.repository.dao

import com.konix.data.dto.request.DematAccountRequestDTO
import com.konix.data.dto.response.DematAccountResponseDTO
import com.konix.data.repository.entity.DematAccounts
import com.konix.util.RecordCreationErrorHandler
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object DematAccountDAO {

    fun createDematAccount(
        userId: Int,
        dematAccountRequestDTO: DematAccountRequestDTO
    ): RecordCreationErrorHandler {
        return transaction {
            val existingRecord = DematAccounts.select { DematAccounts.userId eq userId }.singleOrNull()
            if (existingRecord != null) {
                return@transaction RecordCreationErrorHandler
                    .AlreadyExists("Demat Account Already Exists For This User")
            }
            DematAccounts.insert {
                it[DematAccounts.userId] = userId
                it[accountNumber] = generateAccountNumber()
                it[accountStatus] = "ACTIVE"
                it[brokerName] = dematAccountRequestDTO.brokerName
                it[balance] = 50000.toBigDecimal()
                it[accountHolderName] = dematAccountRequestDTO.accountHolderName
                it[nominee] = dematAccountRequestDTO.nominee
                it[nomineeRelation] = dematAccountRequestDTO.nomineeRelation.name
                it[address] = dematAccountRequestDTO.address
                it[phoneNumber] = dematAccountRequestDTO.phoneNumber
                it[panNumber] = dematAccountRequestDTO.panNumber
                it[openingDate] = LocalDate.now().toString()
            }
            RecordCreationErrorHandler
                .Success("Demat Account Opened Successfully...")
        }
    }

    fun getDematAccountDetails(userId: Int): DematAccountResponseDTO? {
        return transaction {
            DematAccounts.select { DematAccounts.userId eq userId }
                .singleOrNull()?.let {
                    DematAccountResponseDTO(
                        dematAccountId = it[DematAccounts.dematAccountId],
                        accountHolderName = it[DematAccounts.accountHolderName],
                        phoneNumber = it[DematAccounts.phoneNumber],
                        panNumber = it[DematAccounts.panNumber],
                        address = it[DematAccounts.address],
                        openingDate = it[DematAccounts.openingDate],
                        nominee = it[DematAccounts.nominee],
                        nomineeRelation = it[DematAccounts.nomineeRelation],
                        balance = it[DematAccounts.balance].toDouble(),
                        accountStatus = it[DematAccounts.accountStatus],
                        brokerName = it[DematAccounts.brokerName],
                        accountNumber = it[DematAccounts.accountNumber],
                    )
                }
        }
    }
}

fun generateAccountNumber(): String {
    val alphabet = ('A'..'Z')
    val numbers = ('0'..'9')

    // Generate the first 4 alphabetic characters
    val firstPart = (1..4)
        .map { alphabet.random() }
        .joinToString("")

    // Generate the middle 7 numeric characters
    val middlePart = (1..7)
        .map { numbers.random() }
        .joinToString("")

    // Generate the last alphabetic character
    val lastPart = alphabet.random()

    // Combine parts together
    val accountNumber = firstPart + middlePart + lastPart

    return accountNumber
}