package com.konix.service.auth

import com.konix.data.repository.dao.OtpDAO
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

class OtpService(
    private val emailService: EmailService
) {

    private val secureRandom = SecureRandom()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun generateOtp(): String {
        return (1..6)
            .joinToString("") {
                secureRandom.nextInt(10).toString()
            }
    }

    private fun hashOtp(otp: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return Base64
            .getEncoder()
            .encodeToString(md.digest(otp.toByteArray()))
    }

    fun saveOtpInDb(
        userEmail: String,
        otp: String
    ): Boolean {
        val hashOtp = hashOtp(otp)
        val expiry = System.currentTimeMillis() + 8 * 60 * 1000
        val now = dateFormat.format(Date())

        OtpDAO.insertOtpInDB(
            userEmail = userEmail,
            hashedOtp = hashOtp,
            expiry = expiry,
            updatedAt = now
        )

        return true
    }

    fun verifyOtp(
        userEmail: String,
        otp: String
    ): Boolean {
        val hashedOtp = hashOtp(otp)
        val storedOtp = OtpDAO.fetchOtpRecord(userEmail)
        if (
            storedOtp == hashedOtp &&
            System.currentTimeMillis() < OtpDAO.fetchOtpExpiry(userEmail)
        ) {
            OtpDAO.deleteOtpRecord(userEmail)
            return true
        } else {
            return false
        }
    }
}
