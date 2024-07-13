package com.konix.util

sealed class RecordCreationResponse {
    data class Success(val successMessage: String, val userId: Int) : RecordCreationResponse()
    data class AlreadyExists(val errorMessage: String) : RecordCreationResponse()
}