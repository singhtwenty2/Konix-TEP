package com.singhtwenty2.konix.feature_home.data.mapper

import com.singhtwenty2.konix.feature_home.data.remote.dto.StockPriceResponseDTO
import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse

fun StockPriceResponseDTO.toStockPriceResponse(): StockPriceResponse {
    return StockPriceResponse(
        timeStamp = timeStamp,
        price = price
    )
}