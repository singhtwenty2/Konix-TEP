package com.singhtwenty2.data.repository.entity

import org.jetbrains.exposed.sql.Table

object Exchanges : Table() {

    val exchangeId = integer("exchange_id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()
    val symbol = varchar("symbol", 100).uniqueIndex()
    val description = text("description")
    val location = varchar("location", 100)
    val website = varchar("website", 100)
    val timezone = varchar("timezone", 50)
    val openingHours = varchar("opening_hours", 50)
    val closingHours = varchar("closing_hours", 50)
    val currency = varchar("currency", 20)
    val establishedDate = varchar("established_date", 20)

    override val primaryKey = PrimaryKey(exchangeId)
}