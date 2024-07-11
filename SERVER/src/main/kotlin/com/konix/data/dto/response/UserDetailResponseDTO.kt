package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponseDTO(
    val name: String,
    val email: String,
    val age: String,
    val gender: String
)
