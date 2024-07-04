package com.singhtwenty2.configs

import com.singhtwenty2.security.token.JwtTokenService
import com.singhtwenty2.security.token.TokenConfig
import com.singhtwenty2.controller.*
import com.singhtwenty2.service.auth.EmailService
import com.singhtwenty2.service.auth.OtpService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    tokenConfig: TokenConfig,
    tokenService: JwtTokenService,
) {
    val emailService = EmailService(
        apiKey = System.getenv("SENDGRID_API_KEY")
    )
    val otpService = OtpService(emailService)

    routing {

        welcome()
        signup(
            emailService,
            otpService
        )
        login(
            tokenConfig,
            tokenService)
        getSecretInfo()
        kyc()
        dematAccount()
        exchanges()
        company()
        companyExchange()
        stockPrice()
    }
}
