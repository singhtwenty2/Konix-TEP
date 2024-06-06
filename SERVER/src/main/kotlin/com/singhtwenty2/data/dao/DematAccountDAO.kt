package com.singhtwenty2.data.dao

import com.singhtwenty2.data.entity.DematAccounts
import com.singhtwenty2.data.request.DematAccountRequestDTO
import com.singhtwenty2.data.response.DematAccountResponseDTO
import com.singhtwenty2.util.DematAccountCreationResult
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object DematAccountDAO {

    fun createDematAccount(userId: Int, dematAccountRequestDTO: DematAccountRequestDTO): DematAccountCreationResult {
        return transaction {
            val existingRecord = DematAccounts.select { DematAccounts.userId eq userId }.singleOrNull()
            if (existingRecord != null) {
                return@transaction DematAccountCreationResult.AlreadyExists("Demat Account already exists for this user")
            }
            DematAccounts.insert {
                it[DematAccounts.userId] = userId
                it[accountNumber] = generateAccountNumber()
                it[accountStatus] = "ACTIVE"
                it[brokerName] = dematAccountRequestDTO.brokerName
                it[balance] = 5000.toBigDecimal()
                it[accountHolderName] = dematAccountRequestDTO.accountHolderName
                it[nominee] = dematAccountRequestDTO.nominee
                it[nomineeRelation] = dematAccountRequestDTO.nomineeRelation.name
                it[address] = dematAccountRequestDTO.address
                it[phoneNumber] = dematAccountRequestDTO.phoneNumber
                it[panNumber] = dematAccountRequestDTO.panNumber
                it[openingDate] = LocalDate.now().toString()
            }
            DematAccountCreationResult.Success
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