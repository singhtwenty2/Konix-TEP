package com.konix

import com.konix.security.token.JwtTokenService
import com.konix.security.token.TokenConfig
import com.konix.configs.*
import com.konix.data.dto.request.SignupSessionRequestDTO
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    //Setting up JWT
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").toString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureHTTP()
    configureSession()
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig, tokenService)
    //insertDummyStockPrices()
    //stockPriceUpdater()
    //autoInsertionToCompanyExchangeScript()
//    handelSignup(userEmail = "callmeysg@gmail.com")
}
