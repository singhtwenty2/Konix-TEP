package com.konix.service.order

import com.konix.data.dto.request.OrderRequestDTO
import com.konix.data.dto.request.TransactionRequestDTO
import com.konix.data.dto.response.OrderResponseDTO
import com.konix.data.dto.response.TransactionResponsetDTO
import com.konix.data.repository.dao.OrderDAO
import com.konix.data.repository.dao.TransactionDAO
import kotlin.math.min

class OrderService {

    private val orderDao = OrderDAO
    private val transactionDao = TransactionDAO

    fun placeOrder(orderRequestDTO: OrderRequestDTO): OrderResponseDTO {
        val createOrder = orderDao.createOrder(orderRequestDTO)

        matchOrder(createOrder)

        return createOrder
    }

    private fun matchOrder(responseDTO: OrderResponseDTO) {
        val openOrders = orderDao.getOpenOrdersByCompany(responseDTO.companyId)

        for(openOrder in openOrders) {
            if(
                (responseDTO.orderType == "BUY") && (openOrder.orderType == "SELL" && responseDTO.price >= openOrder.price) ||
                (responseDTO.orderType == "SELL") && (openOrder.orderType == "BUY" && responseDTO.price <= openOrder.price)
            ) {
                val matchedQuantity = min(responseDTO.quantity, openOrder.quantity)

                transactionDao.createTransaction(
                    TransactionRequestDTO(
                        orderId = openOrder.orderId,
                        userId = openOrder.userId,
                        companyId = openOrder.companyId,
                        quantity = matchedQuantity,
                        price = openOrder.price
                    )
                )

                responseDTO.quantity -= matchedQuantity
                openOrder.quantity -= matchedQuantity
                val orderId = openOrder.orderId

                if(responseDTO.quantity == 0) {
                    orderDao.updateOrderStatus(orderId, "FILLED")
                    break
                } else {
                    orderDao.updateOrderStatus(openOrder.orderId, "PARTIALLY_FILLED")
                }
                if(openOrder.quantity == 0) {
                    orderDao.updateOrderStatus(orderId, "FILLED")
                }
                else {
                    orderDao.updateOrderStatus(orderId, "PARTIALLY_FILLED")
                }
            }
        }
        if(responseDTO.quantity > 0) {
            orderDao.updateOrderStatus(responseDTO.orderId, "OPEN")
        }
    }

    fun getOrdersByUserId(userId: Int): List<OrderResponseDTO?> {
        return orderDao.getOrdersByUserId(userId)
    }

    fun getTransactionsByUserId(userId: Int): List<TransactionResponsetDTO?> {
        return transactionDao.getTransactionsByUser(userId)
    }
}