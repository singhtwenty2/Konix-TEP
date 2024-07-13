package com.konix.util

sealed class UserCreationErrorHandler {
    data class Success(val successMessage: String, val userId: Int) : UserCreationErrorHandler()
    data class AlreadyExists(val errorMessage: String) : UserCreationErrorHandler()
}