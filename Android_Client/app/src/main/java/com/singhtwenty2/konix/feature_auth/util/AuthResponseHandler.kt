package com.singhtwenty2.konix.feature_auth.util

sealed class AuthResponseHandler<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : AuthResponseHandler<T>(data)
    class UnknownError<T>(message: String, data: T? = null) : AuthResponseHandler<T>(data, message)
    class UnAuthorized<T> : AuthResponseHandler<T>()
    class InternalServerError<T> : AuthResponseHandler<T>()
    class BadRequest<T> : AuthResponseHandler<T>()
}