package com.singhtwenty2.util

sealed class DematAccountCreationResult {
    data object Success : DematAccountCreationResult()
    data class AlreadyExists(val message: String) : DematAccountCreationResult()
}