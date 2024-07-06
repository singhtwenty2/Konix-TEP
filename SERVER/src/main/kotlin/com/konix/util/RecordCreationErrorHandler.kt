package com.konix.util

sealed class RecordCreationErrorHandler {
    data class Success(val successMessage: String) : RecordCreationErrorHandler()
    data class AlreadyExists(val errorMessage: String) : RecordCreationErrorHandler()
}