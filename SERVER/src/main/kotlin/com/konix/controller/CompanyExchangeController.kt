package com.konix.controller

import com.konix.data.repository.dao.CompanyExchangeDAO
import com.konix.data.dto.request.CompanyExchangeRequestDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.companyExchange() {
    authenticate {
        post("/api/v1/company-exchange") {
            val request = call.receive<CompanyExchangeRequestDTO>()
            CompanyExchangeDAO.insertCompanyExchange(request)
            call.respond(HttpStatusCode.OK, "Field inserted successfully...")
        }

        get("/api/v1/company-exchange/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            id?.let {
                val response = CompanyExchangeDAO.getCompanyExchange(companyId = id)
                response.let {
                    call.respond(HttpStatusCode.OK, response)
                }
            }
        }
    }
}