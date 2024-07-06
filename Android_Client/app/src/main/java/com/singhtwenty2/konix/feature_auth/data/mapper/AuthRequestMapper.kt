package com.singhtwenty2.konix.feature_auth.data.mapper

import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.LoginRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.SignupRequestDTO
import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.VerifyOtpRequestDTO
import com.singhtwenty2.konix.feature_auth.domain.model.LoginRequest
import com.singhtwenty2.konix.feature_auth.domain.model.SignupRequest
import com.singhtwenty2.konix.feature_auth.domain.model.VerifyOtpRequest

fun SignupRequestDTO.toSignupRequest(): SignupRequest {
    return SignupRequest(
        name = name,
        email = email,
        age = age,
        gender = gender,
        password = password
    )
}

fun SignupRequest.toSignupRequestDTO(): SignupRequestDTO {
    return SignupRequestDTO(
        name = name,
        email = email,
        age = age,
        gender = gender,
        password = password
    )
}

fun VerifyOtpRequestDTO.toVerifyOtpRequest(): VerifyOtpRequest {
    return VerifyOtpRequest(
        otp = otp
    )
}

fun VerifyOtpRequest.toVerifyOtpRequestDTO(): VerifyOtpRequestDTO {
    return VerifyOtpRequestDTO(
        otp = otp
    )
}

fun LoginRequestDTO.toLoginRequest(): LoginRequest {
    return LoginRequest(
        email = email,
        password = password
    )
}

fun LoginRequest.toLoginRequestDTO(): LoginRequestDTO {
    return LoginRequestDTO(
        email = email,
        password = password
    )
}
