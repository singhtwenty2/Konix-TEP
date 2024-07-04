package com.singhtwenty2.controller

import com.singhtwenty2.data.repository.dao.ExchangeDAO
import com.singhtwenty2.data.dto.request.ExchangeRequestDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.exchanges() {
    authenticate {
        post("/api/v1/exchanges") {
            val requestDTO = call.receive<ExchangeRequestDTO>()
            ExchangeDAO.insertExchange(requestDTO)
            call.respond(HttpStatusCode.OK, "Exchange values uploaded successfully...")
        }

        get("/api/v1/exchanges/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            id?.let {
                val response = ExchangeDAO.getExchanges(id)
                response?.let {
                    call.respond(HttpStatusCode.OK, response)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
    }

}