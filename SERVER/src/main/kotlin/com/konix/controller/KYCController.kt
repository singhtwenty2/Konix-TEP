package com.konix.controller

import com.konix.data.dto.request.KYCDTO
import com.konix.data.repository.dao.KYCDAO
import com.konix.util.RecordCreationErrorHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.kyc() {
    authenticate {
        post("/api/v1/kyc") {
            val requestDTO = call.receive<KYCDTO>()
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                when (val result = KYCDAO.performKYC(userId.toInt(), requestDTO)) {
                    is RecordCreationErrorHandler.AlreadyExists -> {
                        call.respond(
                            HttpStatusCode.Conflict,
                            message = result.errorMessage
                        )
                    }

                    is RecordCreationErrorHandler.Success -> {
                        call.respond(
                            HttpStatusCode.Conflict,
                            message = result.successMessage
                        )
                    }
                }
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
        get("/api/v1/kyc") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                val response = KYCDAO.fetchKYCDetails(userId.toInt())
                response?.let {
                    call.respond(HttpStatusCode.OK, response)
                } ?: call.respond("Empty")
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
    }
}