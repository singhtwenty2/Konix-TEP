package com.singhtwenty2.routing

import com.singhtwenty2.data.dao.KYCDAO
import com.singhtwenty2.data.request.KYCDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.kyc() {
    authenticate {
        post("/kyc") {
            val requestDTO = call.receive<KYCDTO>()
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                KYCDAO.performKYC(userId.toInt(), requestDTO)
                call.respond(HttpStatusCode.OK, "KYC Done Successfully...")
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
        get("/kyc") {
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