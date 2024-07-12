package com.singhtwenty2.konix.feature_profile.util

sealed class ProfileResponseHandler<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ProfileResponseHandler<T>(data)
    class UnknownError<T>(message: String, data: T? = null) : ProfileResponseHandler<T>(data, message)
    class UnAuthorized<T> : ProfileResponseHandler<T>()
    class InternalServerError<T> : ProfileResponseHandler<T>()
    class BadRequest<T> : ProfileResponseHandler<T>()
}