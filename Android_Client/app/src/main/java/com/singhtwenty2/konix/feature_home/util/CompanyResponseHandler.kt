package com.singhtwenty2.konix.feature_home.util

sealed class CompanyResponseHandler<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : CompanyResponseHandler<T>(data)
    class UnknownError<T>(message: String, data: T? = null) : CompanyResponseHandler<T>(data, message)
    class UnAuthorized<T> : CompanyResponseHandler<T>()
    class InternalServerError<T> : CompanyResponseHandler<T>()
    class BadRequest<T> : CompanyResponseHandler<T>()
}