package com.singhtwenty2.konix.feature_auth.util

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleApiCall(apiCall: suspend () -> T): AuthResponseHandler<T> {
    return try {
        val result = apiCall.invoke()
        AuthResponseHandler.Success(result)
    } catch (e: HttpException) {
        when(e.code()) {
            400 -> AuthResponseHandler.BadRequest()
            401 -> AuthResponseHandler.UnAuthorized()
            500 -> AuthResponseHandler.InternalServerError()
            else -> AuthResponseHandler.UnknownError("Unknown error occurred")
        }
    } catch (e: IOException) {
        AuthResponseHandler.UnknownError("Network error occurred")
    } catch (e: Exception) {
        AuthResponseHandler.UnknownError("Unknown error occurred")
    }
}
