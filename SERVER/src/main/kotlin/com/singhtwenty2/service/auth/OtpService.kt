package com.singhtwenty2.service.auth

import java.security.SecureRandom

fun generateOtp(): String {
    val secureRandom = SecureRandom()
    return (1..6)
        .joinToString("") {
            secureRandom.nextInt(10).toString()
        }
}