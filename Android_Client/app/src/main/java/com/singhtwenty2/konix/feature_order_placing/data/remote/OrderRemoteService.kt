package com.singhtwenty2.konix.feature_order_placing.data.remote

import com.singhtwenty2.konix.feature_order_placing.data.remote.dto.request.OrderRequestDTO
import com.singhtwenty2.konix.feature_order_placing.data.remote.dto.response.LatestStockPriceResponseDTO
import com.singhtwenty2.konix.feature_order_placing.data.remote.dto.response.OrderResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderRemoteService {

    @POST("/api/v1/order")
    suspend fun placeOrder(
        @Header("Authorization") token: String,
        @Body orderRequestDTO: OrderRequestDTO
    ): OrderResponseDTO

    @GET("/api/v1/order")
    suspend fun fetchOrderDetails(
        @Header("Authorization") token: String
    ): List<OrderResponseDTO>

    @GET("/api/v1/stockprice")
    suspend fun fetchLatestStockPrice(
        @Header("Authorization") token: String,
        @Query("companyId") companyId: Int
    ): LatestStockPriceResponseDTO
}