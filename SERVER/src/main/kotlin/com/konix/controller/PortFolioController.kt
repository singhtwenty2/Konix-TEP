package com.konix.controller

import com.konix.data.repository.dao.PortfolioDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.portfolio(
    portFolioDao: PortfolioDAO
) {
    authenticate {
        get("/api/v1/portfolio") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)?.toInt()
            userId?.let {
                val response = portFolioDao.getUserPortfolio(it)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
        get("/api/v1/portfolio/{companyId}") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)?.toInt()
            val companyId = call.parameters["companyId"]?.toInt()
            if (userId != null && companyId != null) {
                val response = portFolioDao.getStockQuantity(userId, companyId)
                call.respond(HttpStatusCode.OK, response)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}