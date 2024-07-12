package com.singhtwenty2.konix.feature_profile.util

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleApiCall(apiCall: suspend () -> T): ProfileResponseHandler<T> {
    return try {
        val result = apiCall.invoke()
        ProfileResponseHandler.Success(result)
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> ProfileResponseHandler.BadRequest()
            401 -> ProfileResponseHandler.UnAuthorized()
            500 -> ProfileResponseHandler.InternalServerError()
            else -> ProfileResponseHandler.UnknownError("Unknown error occurred")
        }
    } catch (e: IOException) {
        ProfileResponseHandler.UnknownError("Network error occurred")
    } catch (e: Exception) {
        ProfileResponseHandler.UnknownError("Unknown error occurred")
    }
}