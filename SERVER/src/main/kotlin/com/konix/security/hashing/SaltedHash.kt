package com.singhtwenty2.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
