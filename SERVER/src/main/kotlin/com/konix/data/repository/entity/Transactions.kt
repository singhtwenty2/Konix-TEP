package com.konix.data.repository.entity

import org.jetbrains.exposed.sql.Table

object Transactions : Table() {

    val id = integer("id").autoIncrement()
    val orderId = integer("order_id").references(Orders.id)
    val userId = integer("user_id").references(Users.userId)
    val companyId = integer("company_id").references(Companies.companyId)
    val quantity = integer("quantity")
    val price = decimal("price", 10, 2)
    val timestamp = varchar("timestamp", 40)

    override val primaryKey = PrimaryKey(id)
}