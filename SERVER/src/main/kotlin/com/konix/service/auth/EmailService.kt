package com.singhtwenty2.service.auth

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email

class EmailService(
    apiKey: String,
) {
    private val sendGrid = SendGrid(apiKey)

    fun sendOtpAsEmail(
        userEmail: String,
        otp: String,
    ): Boolean {
        val from = Email(System.getenv("EMAIL"))
        val subject = "OTP for login"
        val to = Email(userEmail)
        val content = Content("text/plain", "Your OTP is $otp")
        val mail = Mail(from, subject, to, content)

        val request = Request()
        request.method = Method.POST
        request.endpoint = "mail/send"
        request.body = mail.build()

        return try {
            sendGrid.api(request)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}