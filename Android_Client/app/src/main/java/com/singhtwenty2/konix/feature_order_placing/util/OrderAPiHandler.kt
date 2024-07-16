package com.singhtwenty2.konix.feature_order_placing.util

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleApiCall(apiCall: suspend () -> T): OrderResponseHandler<T> {
    return try {
        val result = apiCall.invoke()
        OrderResponseHandler.Success(result)
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> OrderResponseHandler.BadRequest()
            401 -> OrderResponseHandler.UnAuthorized()
            500 -> OrderResponseHandler.InternalServerError()
            else -> OrderResponseHandler.UnknownError("Unknown error occurred")
        }
    } catch (e: IOException) {
        OrderResponseHandler.UnknownError("Network error occurred")
    } catch (e: Exception) {
        OrderResponseHandler.UnknownError("Unknown error occurred")
    }
}