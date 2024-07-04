package com.singhtwenty2.service.auth

fun handelSignup(userEmail: String) {
    val otp = generateOtp()
    // Saving OTP in DB

    println(otp)
    val emailService = EmailService(System.getenv("SENDGRID_API_KEY"))
    val isOtpSent = emailService.sendOtpAsEmail(userEmail, otp)

    if (isOtpSent) {
        println("OTP sent successfully")
    } else {
        println("Failed to send OTP")
    }
}