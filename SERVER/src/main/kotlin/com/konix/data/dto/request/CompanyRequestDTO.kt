package com.singhtwenty2.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CompanyRequestDTO(
    val id: Int? = null,
    val name: String,
    val symbol: String,
    val sector: String,
    val marketCap: Double,
    val ipoDate: String,
    val description: String,
    val website: String,
    val headquarters: String,
    val ceo: String,
    val employees: Int,
    val foundedDate: String
)
