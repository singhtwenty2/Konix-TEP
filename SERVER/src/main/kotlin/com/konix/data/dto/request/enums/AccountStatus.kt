package com.singhtwenty2.data.dto.request.enums

import kotlinx.serialization.Serializable

@Serializable
enum class AccountStatus {
    ACTIVE,
    CLOSED,
    BLOCKED,
    HOLD
}