package com.singhtwenty2.konix.feature_home.data.remote.dto

data class FullCompanyDetailsResponseDTO(
    val id: Int,
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