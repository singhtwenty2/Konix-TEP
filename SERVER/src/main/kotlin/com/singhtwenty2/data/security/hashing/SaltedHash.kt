package com.singhtwenty2.data.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
