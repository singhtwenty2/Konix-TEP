package com.singhtwenty2.data.entity

import org.jetbrains.exposed.sql.Table

object CompanyExchanges : Table() {

    val companyId = integer("company_id").references(Companies.companyId)
    val exchangeId = integer("exchange_id").references(Exchanges.exchangeId)

    init {
        uniqueIndex(companyId, exchangeId)
    }
}
