package com.singhtwenty2.konix.feature_order_placing.util

sealed class OrderResponseHandler<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : OrderResponseHandler<T>(data)
    class UnknownError<T>(message: String, data: T? = null) : OrderResponseHandler<T>(data, message)
    class UnAuthorized<T> : OrderResponseHandler<T>()
    class InternalServerError<T> : OrderResponseHandler<T>()
    class BadRequest<T> : OrderResponseHandler<T>()
}