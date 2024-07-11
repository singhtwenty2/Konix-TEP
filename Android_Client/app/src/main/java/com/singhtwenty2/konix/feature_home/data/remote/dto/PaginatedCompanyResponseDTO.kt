package com.singhtwenty2.konix.feature_home.data.remote.dto

data class PaginatedCompanyResponseDTO(
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val items: List<CompanyListingDTO>
)

data class CompanyListingDTO(
    val id: Int,
    val name: String,
    val symbol: String,
    val marketCap: Double
)
