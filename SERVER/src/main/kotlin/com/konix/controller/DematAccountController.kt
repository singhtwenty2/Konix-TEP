package com.singhtwenty2.controller

import com.singhtwenty2.data.dto.request.DematAccountRequestDTO
import com.singhtwenty2.data.repository.dao.DematAccountDAO
import com.singhtwenty2.util.RecordCreationErrorHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dematAccount() {
    authenticate {
        post("/api/v1/demat") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            val request = call.receive<DematAccountRequestDTO>()
            userId?.let {
                val result = DematAccountDAO.createDematAccount(
                    userId = userId.toInt(),
                    dematAccountRequestDTO = request
                )
                when (result) {
                    is RecordCreationErrorHandler.Success -> {
                        call.respond(
                            HttpStatusCode.OK,
                            message = result.successMessage
                        )
                    }

                    is RecordCreationErrorHandler.AlreadyExists -> {
                        call.respond(
                            HttpStatusCode.Conflict,
                            message = result.errorMessage
                        )
                    }
                }
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }

        get("/api/v1/demat") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                val dematAccountResponseDTO = DematAccountDAO.getDematAccountDetails(userId = userId.toInt())
                dematAccountResponseDTO?.let {
                    call.respond(HttpStatusCode.OK, dematAccountResponseDTO)
                } ?: call.respond("Empty")
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
    }
}
