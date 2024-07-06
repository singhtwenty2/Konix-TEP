package com.konix.data.repository.entity

import com.konix.data.dto.request.enums.AccountStatus
import com.konix.data.dto.request.enums.NomineeRelation
import org.jetbrains.exposed.sql.Table

object DematAccounts : Table() {

    val dematAccountId = integer("demat_account_id").autoIncrement()
    val userId = integer("user_id").references(Users.userId).uniqueIndex()
    val accountNumber = varchar("account_number", 50).uniqueIndex()
    val accountHolderName = varchar("account_holder_name", 100)
    val phoneNumber = varchar("phone_number", 20).uniqueIndex()
    val address = text("address")
    val panNumber = varchar("pan_number", 20).uniqueIndex()
    val nominee = varchar("nominee", 100)

    @OptIn(ExperimentalStdlibApi::class)
    val nomineeRelation =
        varchar("nominee_relation", 50).check { it -> it inList NomineeRelation.entries.map { it.name } }
    val openingDate = varchar("opening_date", 20)

    @OptIn(ExperimentalStdlibApi::class)
    val accountStatus = varchar("account_status", 50).check { it -> it inList AccountStatus.entries.map { it.name } }
    val brokerName = varchar("broker_name", 100)
    val balance = decimal("balance", 10, 2)

    override val primaryKey = PrimaryKey(dematAccountId)
}