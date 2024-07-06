package com.konix.configs

import com.konix.data.dto.request.SignupSessionRequestDTO
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSession() {
    install(Sessions) {
        cookie<SignupSessionRequestDTO>("signup_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 600
        }
    }
}