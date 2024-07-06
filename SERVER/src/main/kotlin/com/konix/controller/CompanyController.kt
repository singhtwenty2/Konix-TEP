package com.konix.controller

import com.konix.data.repository.dao.CompanyDAO
import com.konix.data.dto.request.CompanyRequestDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.company() {
    authenticate {
        post("/api/v1/company") {
            val request = call.receive<CompanyRequestDTO>()
            CompanyDAO.insertCompany(request)
            call.respond(HttpStatusCode.OK, "Company inserted successfully...")
        }

        get("/api/v1/company/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            id?.let {
                val response = CompanyDAO.getCompany(id)
                response?.let {
                    call.respond(HttpStatusCode.OK, response)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

        get("/api/v1/companies") {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10

            val response = CompanyDAO.getAllCompanies(
                page = page,
                size = size
            )
            response.let {
                call.respond(HttpStatusCode.OK, response)
            }
        }
    }
}