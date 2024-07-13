package com.konix.configs

import com.konix.controller.*
import com.konix.data.repository.dao.PortfolioDAO
import com.konix.security.token.JwtTokenService
import com.konix.security.token.TokenConfig
import com.konix.service.auth.EmailService
import com.konix.service.auth.OtpService
import com.konix.service.order.OrderService
import com.konix.service.user.UserService
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
    val userService = UserService()

    routing {
        health()
        signup(
            emailService,
            otpService,
            userService
        )
        login(
            tokenConfig,
            tokenService,
            userService
        )
        getSecretInfo(
            userService
        )
        kyc()
        dematAccount()
        exchanges()
        company()
        companyExchange()
        stockPrice()
        orderRoutes(
            OrderService()
        )
        portfolio(
            PortfolioDAO
        )
    }
}
