package com.singhtwenty2.app_routing

import com.singhtwenty2.data.dao.UserDAO
import com.singhtwenty2.data.request.LoginRequestDTO
import com.singhtwenty2.data.request.SignupRequestDTO
import com.singhtwenty2.data.response.LoginResponseDTO
import com.singhtwenty2.data.security.token.TokenClaim
import com.singhtwenty2.data.security.token.TokenConfig
import com.singhtwenty2.data.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.welcome() {
    get("/") {
        call.respond("Hello There !")
    }
}

fun Route.signup() {
    post("/api/v1/signup") {
        val requestDTO = call.receive<SignupRequestDTO>()
        UserDAO.createUser(requestDTO)
        call.respond(HttpStatusCode.OK, "Created Account Successfully...")
    }
}

fun Route.login(
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    post("/api/v1/login") {
        val requestDTO = call.receive<LoginRequestDTO>()
        val user = UserDAO.loginUser(requestDTO)
        if(user != null) {
            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.userId.toString()
                )
            )
            call.respond(
                HttpStatusCode.OK,
                message = LoginResponseDTO(
                    token = token
                )
            )
            return@post
        } else {
            call.respond(HttpStatusCode.NotFound, "User Not Found...")
            return@post
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("/api/v1/secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your User Id Is $userId")
        }
    }
}