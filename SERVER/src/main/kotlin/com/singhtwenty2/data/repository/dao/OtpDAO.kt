package com.singhtwenty2.data.repository.dao

import com.singhtwenty2.data.repository.entity.Otps
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object OtpDAO {

    fun insertOtpInDB(
        userEmail: String,
        hashedOtp: String,
        expiry: Long,
        updatedAt: String
    ) {
        return transaction {
            val existingRecord = Otps.select {
                Otps.userEmail eq userEmail
            }.singleOrNull()
            if (existingRecord != null) {
                Otps.update(
                    { Otps.userEmail eq userEmail }
                ) {
                    it[Otps.userEmail] = userEmail
                    it[Otps.hashedOtp] = hashedOtp
                    it[Otps.expiry] = expiry
                    it[Otps.updatedAt] = updatedAt
                }
            } else {
                Otps.insert {
                    it[Otps.userEmail] = userEmail
                    it[Otps.hashedOtp] = hashedOtp
                    it[Otps.expiry] = expiry
                    it[Otps.updatedAt] = updatedAt
                    it[createdAt] = updatedAt
                }
            }
        }
    }

    fun fetchOtpRecord(userEmail: String): String {
        return transaction {
            val storedOtp = Otps.select { Otps.userEmail eq userEmail }.singleOrNull()
            val otp = storedOtp?.get(Otps.hashedOtp) ?: ""
            otp
        }
    }

    fun fetchOtpExpiry(userEmail: String): Long {
        return transaction {
            val storedOtp = Otps.select { Otps.userEmail eq userEmail }.singleOrNull()
            val expiry = storedOtp?.get(Otps.expiry) ?: 0L
            expiry
        }
    }

    fun deleteOtpRecord(userEmail: String) {
        return transaction {
            Otps.deleteWhere { Otps.userEmail eq userEmail }
        }
    }
}