package com.singhtwenty2.konix.feature_home.data.mapper

import com.singhtwenty2.konix.feature_home.data.remote.dto.FullCompanyDetailsResponseDTO
import com.singhtwenty2.konix.feature_home.domain.model.FullCompanyDetailsResponse

fun FullCompanyDetailsResponseDTO
    .toFullCompanyDetailsResposse(): FullCompanyDetailsResponse {
    return FullCompanyDetailsResponse(
        id = id,
        name = name,
        symbol = symbol,
        sector = sector,
        marketCap = marketCap,
        ipoDate = ipoDate,
        description = description,
        website = website,
        headquarters = headquarters,
        ceo = ceo,
        employees = employees,
        foundedDate = foundedDate
    )
}

fun FullCompanyDetailsResponse
        .toFullCompanyDetailsResposseDTO(): FullCompanyDetailsResponseDTO {
    return FullCompanyDetailsResponseDTO(
        id = id,
        name = name,
        symbol = symbol,
        sector = sector,
        marketCap = marketCap,
        ipoDate = ipoDate,
        description = description,
        website = website,
        headquarters = headquarters,
        ceo = ceo,
        employees = employees,
        foundedDate = foundedDate
    )
}

