package com.konix.controller

import com.konix.data.dto.request.*
import com.konix.data.dto.response.LoginResponseDTO
import com.konix.data.repository.dao.DematAccountDAO
import com.konix.data.repository.dao.KYCDAO
import com.konix.security.token.TokenClaim
import com.konix.security.token.TokenConfig
import com.konix.security.token.TokenService
import com.konix.service.auth.EmailService
import com.konix.service.auth.OtpService
import com.konix.service.user.UserService
import com.konix.util.RecordCreationErrorHandler
import com.konix.util.RecordCreationResponse
import com.konix.util.UserCreationErrorHandler
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

fun Route.signup(
    emailService: EmailService,
    otpService: OtpService,
    userService: UserService  // Inject UserService dependency
) {
    post("/api/v1/auth/signup") {
        val requestDTO = call.receive<SignupRequestDTO>()
        val userEmail = requestDTO.email
        logger.info("Received signup request for email: $userEmail")

        if (userService.checkUserExists(userEmail)) {
            logger.warn("User already exists with email: $userEmail")
            call.respond(HttpStatusCode.Conflict, "User Already Exists With This Email $userEmail...")
            return@post
        } else {
            val otp = otpService.generateOtp()
            if (emailService.sendOtpAsEmail(userEmail = userEmail, otp = otp)) {
                logger.info("OTP sent to email: $userEmail")

                call.sessions.set(SignupSessionRequestDTO(
                    email = requestDTO.email,
                    name = requestDTO.name,
                    age = requestDTO.age,
                    gender = requestDTO.gender,
                    password = requestDTO.password
                ))
                call.respond(HttpStatusCode.OK, "OTP Sent To $userEmail...")
                otpService.saveOtpInDb(userEmail, otp)
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
                when (val result = userService.createUser(session)) {
                    is RecordCreationResponse.AlreadyExists -> {
                        logger.warn("User already exists during creation for email: ${session.email}")
                        call.respond(HttpStatusCode.Conflict, result.errorMessage)
                    }
                    is RecordCreationResponse.Success -> {
                        logger.info("User created successfully for email: ${session.email}")
                        // Initialize portfolio
                        userService.initializePortfolio(result.userId)
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
    tokenService: TokenService,
    userService: UserService  // Inject UserService dependency
) {
    post("/api/v1/auth/login") {
        val requestDTO = call.receive<LoginRequestDTO>()
        logger.info("Received login request for email: ${requestDTO.email}")

        val user = userService.loginUser(requestDTO)
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

fun Route.getSecretInfo(
    userService: UserService  // Inject UserService dependency
) {
    authenticate {
        get("/api/v1/auth/secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            logger.info("Accessed secret info for userId: $userId")
            call.respond(HttpStatusCode.OK, "Your User Id Is $userId")
        }
        get("/api/v1/auth/about") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)?.toInt()
            userId?.let {
                val userDetails = userService.getUserDetails(userId)
                userDetails?.let {
                    call.respond(HttpStatusCode.OK, userDetails)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
        get("/api/v1/auth/status") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)?.toInt()
            userId?.let {
                val kycStatus = KYCDAO.isKYCDoneForUser(userId)
                val dematStatus = DematAccountDAO.isDematAccountExistsForUser(userId)
                call.respond(HttpStatusCode.OK, mapOf(
                    "KYC DONE" to kycStatus,
                    "DEMAT ACCOUNT CREATED" to dematStatus
                ))
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
        get("/api/v1/auth/logout") {
            call.sessions.clear<SignupSessionRequestDTO>()
            call.respond(HttpStatusCode.OK, "Logged Out Successfully...")
        }
    }
}