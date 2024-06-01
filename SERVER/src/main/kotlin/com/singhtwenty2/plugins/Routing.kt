package com.singhtwenty2.plugins

import com.singhtwenty2.data.security.token.JwtTokenService
import com.singhtwenty2.data.security.token.TokenConfig
import com.singhtwenty2.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
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
    }
}
