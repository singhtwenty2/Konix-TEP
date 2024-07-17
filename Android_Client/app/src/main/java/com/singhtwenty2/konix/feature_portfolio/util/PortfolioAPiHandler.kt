package com.singhtwenty2.konix.feature_portfolio.util

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleApiCall(apiCall: suspend () -> T): PortfolioResponseHandler<T> {
    return try {
        val result = apiCall.invoke()
        PortfolioResponseHandler.Success(result)
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> PortfolioResponseHandler.BadRequest()
            401 -> PortfolioResponseHandler.UnAuthorized()
            500 -> PortfolioResponseHandler.InternalServerError()
            else -> PortfolioResponseHandler.UnknownError("Unknown error occurred")
        }
    } catch (e: IOException) {
        PortfolioResponseHandler.UnknownError("Network error occurred")
    } catch (e: Exception) {
        PortfolioResponseHandler.UnknownError("Unknown error occurred")
    }
}