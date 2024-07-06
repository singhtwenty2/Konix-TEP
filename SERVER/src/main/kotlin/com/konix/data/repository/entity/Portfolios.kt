package com.konix.data.repository.entity

import org.jetbrains.exposed.sql.Table

object Portfolios : Table() {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(Users.userId)
    val companyId = integer("company_id").references(Companies.companyId)
    val quantity = integer("quantity")
    val averagePrice = decimal("average_price", 10, 2)

    override val primaryKey = PrimaryKey(id)
}