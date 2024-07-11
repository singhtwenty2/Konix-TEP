package com.singhtwenty2.konix.feature_home.data.mapper

import com.singhtwenty2.konix.feature_home.data.remote.dto.CompanyListingDTO
import com.singhtwenty2.konix.feature_home.data.remote.dto.PaginatedCompanyResponseDTO
import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing

fun CompanyListingDTO.toCompanyListing(): CompanyListing {
    return CompanyListing(
        id = id,
        name = name,
        symbol = symbol,
        marketCap = marketCap
    )
}

fun PaginatedCompanyResponseDTO.toCompanyListingList(): List<CompanyListing> {
    return items.map { it.toCompanyListing() }
}

