package com.konix.configs

import com.konix.controller.*
import com.konix.security.token.JwtTokenService
import com.konix.security.token.TokenConfig
import com.konix.service.auth.EmailService
import com.konix.service.auth.OtpService
import com.konix.service.order.OrderService
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
            tokenService
        )
        getSecretInfo()
        kyc()
        dematAccount()
        exchanges()
        company()
        companyExchange()
        stockPrice()
        orderRoutes(
            OrderService()
        )
    }
}
