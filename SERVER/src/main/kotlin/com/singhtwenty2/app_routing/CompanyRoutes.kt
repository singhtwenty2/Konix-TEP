package com.singhtwenty2.app_routing

import com.singhtwenty2.data.dao.CompanyDAO
import com.singhtwenty2.data.request.CompanyRequestDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.company() {
    authenticate {
        post("/company") {
            val request = call.receive<CompanyRequestDTO>()
            CompanyDAO.insertCompany(request)
            call.respond(HttpStatusCode.OK, "Company inserted successfully...")
        }

        get("/company/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            id?.let {
                val response = CompanyDAO.getCompany(id)
                response?.let {
                    call.respond(HttpStatusCode.OK, response)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}