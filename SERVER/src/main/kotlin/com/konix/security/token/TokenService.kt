package com.singhtwenty2.security.token

interface TokenService {
    fun generate(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String
}