package com.singhtwenty2.konix.feature_auth.data.remote.dto.response

import com.squareup.moshi.Json

data class UserStatusResponseDTO(
    @field:Json(name = "KYC DONE") val isKycDone: Boolean,
    @field:Json(name = "DEMAT ACCOUNT CREATED") val isDematCreated: Boolean,
)
