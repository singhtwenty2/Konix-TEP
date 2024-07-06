package com.konix.data.repository.entity

import com.konix.data.dto.request.enums.OrderStatus
import com.konix.data.dto.request.enums.OrderType
import org.jetbrains.exposed.sql.Table

object Orders : Table() {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(Users.userId)
    val companyId = integer("company_id").references(Companies.companyId)
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val createdAt = varchar("created_at", 50)
    val updatedAt = varchar("updated_at", 50)

    @OptIn(ExperimentalStdlibApi::class)
    val orderType =
        varchar("order_type", 50)
            .check { it -> it inList OrderType.entries.map { it.name } }

    @OptIn(ExperimentalStdlibApi::class)
    val orderStatus =
        varchar("order_status", 50)
            .check { it -> it inList OrderStatus.entries.map { it.name } }

    override val primaryKey = PrimaryKey(id)
}