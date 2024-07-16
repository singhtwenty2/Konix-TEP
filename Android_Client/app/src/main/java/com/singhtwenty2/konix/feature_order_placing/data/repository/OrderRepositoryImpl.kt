package com.singhtwenty2.konix.feature_order_placing.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.singhtwenty2.konix.feature_order_placing.data.mapper.toOrderRequestDTO
import com.singhtwenty2.konix.feature_order_placing.data.mapper.toOrderResponse
import com.singhtwenty2.konix.feature_order_placing.data.remote.OrderRemoteService
import com.singhtwenty2.konix.feature_order_placing.domain.model.LatestStockPriceResponse
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderRequest
import com.singhtwenty2.konix.feature_order_placing.domain.model.OrderResponse
import com.singhtwenty2.konix.feature_order_placing.domain.repository.OrderRepository
import com.singhtwenty2.konix.feature_order_placing.util.OrderResponseHandler
import com.singhtwenty2.konix.feature_order_placing.util.handleApiCall

class OrderRepositoryImpl(
    private val service: OrderRemoteService,
    private val pref: SharedPreferences
) : OrderRepository {

    override suspend fun placeOrder(orderRequest: OrderRequest): OrderResponseHandler<OrderResponse> {
        return handleApiCall {
            val response = service.placeOrder(
                token = "Bearer ${pref.getString("jwt", "")}",
                orderRequestDTO = orderRequest.toOrderRequestDTO()
            )
            response.toOrderResponse()
        }
    }

    override suspend fun fetchOrder(): OrderResponseHandler<List<OrderResponse>> {
        return handleApiCall {
            val response = service.fetchOrderDetails(
                token = "Bearer ${pref.getString("jwt", "")}"
            )
            response.map { it.toOrderResponse() }
        }
    }

    override suspend fun fetchLatestStockPrice(companyId: Int): OrderResponseHandler<LatestStockPriceResponse> {
        return handleApiCall {
            val response = service.fetchLatestStockPrice(
                token = "Bearer ${pref.getString("jwt", "")}",
                companyId = companyId
            )
            Log.d("token", "fetchLatestStockPrice: token = ${pref.getString("jwt", "")}")
            LatestStockPriceResponse(
                price = response.price,
            )
        }
    }
}