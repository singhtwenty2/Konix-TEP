package com.konix.data.repository.entity

import org.jetbrains.exposed.sql.Table

object Otps: Table() {
    val otpId = integer("otp_id").autoIncrement()
    val userEmail = varchar("user_email", 100).uniqueIndex()
    val hashedOtp = varchar("hashed_otp", 64)
    val createdAt = varchar("created_at", 50)
    val updatedAt = varchar("updated_at", 50)
    val expiry = long("expiry")
    override val primaryKey = PrimaryKey(otpId)
}