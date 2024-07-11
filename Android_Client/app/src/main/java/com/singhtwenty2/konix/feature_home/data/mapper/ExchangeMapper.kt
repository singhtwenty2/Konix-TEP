package com.singhtwenty2.konix.feature_home.data.mapper

import com.singhtwenty2.konix.feature_home.data.remote.dto.ExchangeDetailsResponseDTO
import com.singhtwenty2.konix.feature_home.domain.model.ExchangeDetailsResponse

fun ExchangeDetailsResponseDTO
    .toExchangeDetailsResponse(): ExchangeDetailsResponse {
    return ExchangeDetailsResponse(
        id = id,
        name = name,
        symbol = symbol,
        description = description,
        location = location,
        website = website,
        timeZone = timeZone,
        openingHours = openingHours,
        closingHours = closingHours,
        currency = currency,
        establishedDate = establishedDate
    )
}