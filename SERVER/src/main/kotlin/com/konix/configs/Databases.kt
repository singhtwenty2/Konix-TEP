package com.konix.configs

import com.konix.data.repository.entity.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    Database.connect(
        url = System.getenv("JDBC_URL"),
        driver = System.getenv("DRIVER"),
        user = System.getenv("USER"),
        password = System.getenv("PASSWORD")
    )
    transaction {

        SchemaUtils.create(
            Users,
            KYC,
            DematAccounts,
            Companies,
            Exchanges,
            CompanyExchanges,
            StockPrices,
            Orders,
            Transactions,
            Portfolios,
            Otps
        )
    }
}
