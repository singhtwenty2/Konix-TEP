package com.singhtwenty2

import com.singhtwenty2.data.security.token.JwtTokenService
import com.singhtwenty2.data.security.token.TokenConfig
import com.singhtwenty2.plugins.*
import com.singhtwenty2.util.autoInsertionToCompanyExchangeScript
import io.ktor.server.application.*

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
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig, tokenService)
    //autoInsertionToCompanyExchangeScript()
}
