package com.konix.data.repository.entity

import org.jetbrains.exposed.sql.Table

object CompanyExchanges : Table() {

    val id = integer("id").autoIncrement()
    val companyId = integer("company_id").references(Companies.companyId)
    val exchangeId = integer("exchange_id").references(Exchanges.exchangeId)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(companyId, exchangeId)
    }
}