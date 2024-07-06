package com.konix.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponseDTO<T>(
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val items: List<T>
)
