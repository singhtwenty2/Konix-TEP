package com.singhtwenty2.util

sealed class OtpRecordInsertionResult {
    data class OtpCanBeInsertedDirectly(val successMessage: String) : OtpRecordInsertionResult()
    data class OtpAlreadyExists(val errorMessage: String) : OtpRecordInsertionResult()
}