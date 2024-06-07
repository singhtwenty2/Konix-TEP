package com.singhtwenty2.data.entity

import com.singhtwenty2.data.request.enums.Gender
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val userId = integer("user_id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 100).uniqueIndex()
    val age = integer("age")
    @OptIn(ExperimentalStdlibApi::class)
    val gender = varchar("gender", 50).check { it -> it inList Gender.entries.map { it.name } }
    val password = varchar("hashed_password", 100)
    val salt = varchar("salt", 100)

    override val primaryKey = PrimaryKey(userId)
}