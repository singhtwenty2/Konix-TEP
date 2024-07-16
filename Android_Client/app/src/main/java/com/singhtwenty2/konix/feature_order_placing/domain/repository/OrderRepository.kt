package com.singhtwenty2.konix.feature_order_placing.domain.repository

import com.singhtwenty2.konix.feature_order_placing.domain.model.LatestStockPriceResponse
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderRequest
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse
import com.singhtwenty2.konix.feature_order_placing.util.OrderResponseHandler

interface OrderRepository {

    suspend fun placeOrder(
        orderRequest: OrderRequest
    ): OrderResponseHandler<OrderResponse>

    suspend fun fetchOrder(): OrderResponseHandler<List<OrderResponse>>

    suspend fun fetchLatestStockPrice(
        companyId: Int
    ): OrderResponseHandler<LatestStockPriceResponse>
}