package com.konix.controller

import com.konix.data.dto.request.OrderRequestDTO
import com.konix.service.order.OrderService
import com.konix.util.DematAccountNotFoundException
import com.konix.util.InsufficientBalanceException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRoutes(orderService: OrderService) {
    authenticate {
        post("/api/v1/order") {
            val request = call.receive<OrderRequestDTO>()
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)?.toInt()
            userId?.let {
                try {
                    val response = orderService.placeOrder(userId = it, orderRequestDTO = request)
                    call.respond(HttpStatusCode.OK, response)
                } catch (e: InsufficientBalanceException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Insufficient Balance")
                } catch (e: DematAccountNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "Demat Account Not Found")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
                }
            } ?: call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
        }

        get("/api/v1/order") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                val orders = orderService.getOrdersByUserId(it.toInt())
                orders.let {
                    call.respond(HttpStatusCode.OK, orders)
                }
            } ?: call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
        }

        get("/api/v1/transactions") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                val transactions = orderService.getTransactionsByUserId(it.toInt())
                transactions.let {
                    call.respond(HttpStatusCode.OK, transactions)
                }
            } ?: call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
        }
    }
}
