package com.singhtwenty2.app_routing

import com.singhtwenty2.data.dao.DematAccountDAO
import com.singhtwenty2.data.request.DematAccountRequestDTO
import com.singhtwenty2.util.DematAccountCreationResult
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
                    is DematAccountCreationResult.Success -> {
                        call.respond(HttpStatusCode.OK, "Demat Account Opened Successfully...")
                    }
                    is DematAccountCreationResult.AlreadyExists -> {
                        call.respond(HttpStatusCode.Conflict, result.message)
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
