package com.singhtwenty2.konix.feature_portfolio.util

sealed class PortfolioResponseHandler<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : PortfolioResponseHandler<T>(data)
    class UnknownError<T>(message: String, data: T? = null) : PortfolioResponseHandler<T>(data, message)
    class UnAuthorized<T> : PortfolioResponseHandler<T>()
    class InternalServerError<T> : PortfolioResponseHandler<T>()
    class BadRequest<T> : PortfolioResponseHandler<T>()
}