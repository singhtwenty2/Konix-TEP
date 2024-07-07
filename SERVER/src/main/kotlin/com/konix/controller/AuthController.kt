package com.konix.controller

import com.konix.data.dto.request.*
import com.konix.data.dto.response.LoginResponseDTO
import com.konix.data.repository.dao.UserDAO
import com.konix.security.token.TokenClaim
import com.konix.security.token.TokenConfig
import com.konix.security.token.TokenService
import com.konix.service.auth.EmailService
import com.konix.service.auth.OtpService
import com.konix.util.RecordCreationErrorHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Controller")

fun Route.welcome() {
    get("/api/v1/") {
        call.respond("Hello There !")
    }
}

fun Route.signup(
    emailService: EmailService,
    otpService: OtpService
) {
    post("/api/v1/auth/signup") {
        val requestDTO = call.receive<SignupRequestDTO>()
        val userEmail = requestDTO.email
        logger.info("Received signup request for email: $userEmail")

        if(UserDAO.checkUserExists(email = userEmail)) {
            logger.warn("User already exists with email: $userEmail")
            call.respond(HttpStatusCode.Conflict, "User Already Exists With This Email $userEmail...")
            return@post
        } else {
            if(emailService.sendOtpAsEmail(userEmail = userEmail, otp = otpService.generateOtp())) {
                logger.info("OTP sent to email: $userEmail")
                call.sessions.set(SignupSessionRequestDTO(
                    email = requestDTO.email,
                    name = requestDTO.name,
                    age = requestDTO.age,
                    gender = requestDTO.gender,
                    password = requestDTO.password
                ))
                call.respond(HttpStatusCode.OK, "OTP Sent To $userEmail...")
                otpService.saveOtpInDb(userEmail)
            } else {
                logger.error("Failed to send OTP to email: $userEmail")
                call.respond(HttpStatusCode.InternalServerError, "Failed To Send OTP To $userEmail...")
            }
        }
    }

    post("/api/v1/auth/verify-otp") {
        val requestDTO = call.receive<VerifyOtpRequestDTO>()
        val session = call.sessions.get<SignupSessionRequestDTO>()
        logger.info("Received OTP verification request for email: ${session?.email}")

        if (session != null) {
            if (otpService.verifyOtp(userEmail = session.email, otp = requestDTO.otp)) {
                logger.info("OTP verified for email: ${session.email}")
                when (val result = UserDAO.createUser(session)) {
                    is RecordCreationErrorHandler.AlreadyExists -> {
                        logger.warn("User already exists during creation for email: ${session.email}")
                        call.respond(HttpStatusCode.Conflict, result.errorMessage)
                    }
                    is RecordCreationErrorHandler.Success -> {
                        logger.info("User created successfully for email: ${session.email}")
                        call.sessions.clear<SignupSessionRequestDTO>()
                        call.respond(HttpStatusCode.OK, result.successMessage)
                    }
                }
            } else {
                logger.warn("Invalid OTP for email: ${session.email}")
                call.respond(HttpStatusCode.Unauthorized, "Invalid OTP...")
            }
        } else {
            logger.error("Session not found for OTP verification")
            call.respond(HttpStatusCode.BadRequest, "Session Not Found...")
        }
    }
}

fun Route.login(
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    post("/api/v1/auth/login") {
        val requestDTO = call.receive<LoginRequestDTO>()
        logger.info("Received login request for email: ${requestDTO.email}")

        val user = UserDAO.loginUser(requestDTO)
        if (user != null) {
            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(name = "userId", value = user.userId.toString())
            )
            logger.info("User logged in successfully: ${user.userId}")
            call.respond(HttpStatusCode.OK, message = LoginResponseDTO(token = token))
        } else {
            logger.warn("User not found for email: ${requestDTO.email}")
            call.respond(HttpStatusCode.NotFound, "User Not Found...")
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("/api/v1/secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            logger.info("Accessed secret info for userId: $userId")
            call.respond(HttpStatusCode.OK, "Your User Id Is $userId")
        }
    }
}
