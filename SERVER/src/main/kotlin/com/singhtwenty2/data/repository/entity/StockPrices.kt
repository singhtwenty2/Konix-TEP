package com.singhtwenty2.data.repository.entity

import org.jetbrains.exposed.sql.Table

object StockPrices : Table() {

    val priceId = integer("price_id").autoIncrement()
    val companyId = integer("company_id").references(Companies.companyId)
    val price = decimal("price", 10, 2)
    val timestamp = varchar("timestamp", 40)

    override val primaryKey = PrimaryKey(priceId)
}