package com.konix.data.dto.request.enums

import kotlinx.serialization.Serializable

@Serializable
enum class EmploymentStatus {
    EMPLOYED,
    SELF_EMPLOYED,
    UNEMPLOYED,
    STUDENT,
    RETIRED,
    OTHER
}