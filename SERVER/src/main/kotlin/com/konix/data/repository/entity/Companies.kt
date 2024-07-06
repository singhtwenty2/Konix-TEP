package com.konix.data.repository.entity

import org.jetbrains.exposed.sql.Table

object Companies : Table() {

    val companyId = integer("company_id").autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val symbol = varchar("symbol", 50).uniqueIndex()
    val sector = varchar("sector", 100)
    val marketCap = decimal("market_cap", 15, 2)
    val ipoDate = varchar("ipo_date", 20)
    val description = text("description")
    val website = varchar("website", 255)
    val headquarters = varchar("headquarters", 255)
    val ceo = varchar("ceo", 100)
    val employees = integer("employees")
    val foundedDate = varchar("founded_date", 20)

    override val primaryKey = PrimaryKey(companyId)
}