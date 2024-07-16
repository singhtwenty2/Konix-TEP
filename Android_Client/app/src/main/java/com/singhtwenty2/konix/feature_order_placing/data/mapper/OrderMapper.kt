package com.singhtwenty2.konix.feature_order_placing.data.mapper

import com.singhtwenty2.konix.feature_order_placing.data.remote.dto.request.OrderRequestDTO
import com.singhtwenty2.konix.feature_order_placing.data.remote.dto.response.OrderResponseDTO
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderRequest
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse

fun OrderRequest.toOrderRequestDTO(): OrderRequestDTO {
    return OrderRequestDTO(
        companyId = companyId,
        orderType = orderType,
        price = price,
        quantity = quantity
    )
}

fun OrderResponseDTO.toOrderResponse(): OrderResponse {
    return OrderResponse(
        orderId = orderId,
        userId = userId,
        companyId = companyId,
        orderType = orderType,
        orderStatus = orderStatus,
        price = price,
        quantity = quantity,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}