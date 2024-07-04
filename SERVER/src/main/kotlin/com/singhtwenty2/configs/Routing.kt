package com.singhtwenty2.configs

import com.singhtwenty2.security.token.JwtTokenService
import com.singhtwenty2.security.token.TokenConfig
import com.singhtwenty2.controller.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    tokenConfig: TokenConfig,
    tokenService: JwtTokenService
) {
    routing {

        welcome()
        signup()
        login(tokenConfig, tokenService)
        getSecretInfo()
        kyc()
        dematAccount()
        exchanges()
        company()
        companyExchange()
        stockPrice()
    }
}
