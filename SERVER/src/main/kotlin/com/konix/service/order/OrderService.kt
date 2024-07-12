package com.konix.service.order

import com.konix.data.dto.request.OrderRequestDTO
import com.konix.data.dto.request.TransactionRequestDTO
import com.konix.data.dto.response.DematAccountResponseDTO
import com.konix.data.dto.response.OrderResponseDTO
import com.konix.data.dto.response.TransactionResponsetDTO
import com.konix.data.repository.dao.DematAccountDAO
import com.konix.data.repository.dao.OrderDAO
import com.konix.data.repository.dao.TransactionDAO
import com.konix.util.DematAccountNotFoundException
import com.konix.util.InsufficientBalanceException
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.min

class OrderService {

    private val orderDao = OrderDAO
    private val transactionDao = TransactionDAO
    private val dematAccountDao = DematAccountDAO

    fun placeOrder(userId: Int, orderRequestDTO: OrderRequestDTO): OrderResponseDTO {
        val dematAccount = dematAccountDao.getDematAccountDetails(userId)
            ?: throw DematAccountNotFoundException("Demat Account Not Found")

        // Check for sufficient balance
        if (orderRequestDTO.orderType.name == "BUY" && dematAccount.balance < (orderRequestDTO.price * orderRequestDTO.quantity)) {
            throw InsufficientBalanceException("Insufficient Balance")
        }

        // To ensure atomicity
        return transaction {
            // Place order
            val createOrder = orderDao.createOrder(
                userId = userId,
                requestDTO = orderRequestDTO
            )

            // Update balance
            updateBalance(userId, orderRequestDTO, dematAccount)

            // Match orders
            matchOrder(createOrder)

            createOrder
        }
    }

    private fun updateBalance(userId: Int, orderRequestDTO: OrderRequestDTO, dematAccount: DematAccountResponseDTO) {
        val totalAmount = orderRequestDTO.price * orderRequestDTO.quantity
        val newBalance = when (orderRequestDTO.orderType.name) {
            "BUY" -> dematAccount.balance - totalAmount
            "SELL" -> dematAccount.balance + totalAmount
            else -> dematAccount.balance
        }

        dematAccountDao.updateBalance(userId, newBalance.toBigDecimal())
    }

    private fun matchOrder(responseDTO: OrderResponseDTO) {
        val openOrders = orderDao.getOpenOrdersByCompany(responseDTO.companyId)

        transaction {
            for (openOrder in openOrders) {
                try {
                    if (
                        (responseDTO.orderType == "BUY" && openOrder.orderType == "SELL" && responseDTO.price >= openOrder.price) ||
                        (responseDTO.orderType == "SELL" && openOrder.orderType == "BUY" && responseDTO.price <= openOrder.price)
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

                        if (responseDTO.quantity == 0) {
                            orderDao.updateOrderStatus(responseDTO.orderId, "FILLED")
                            break
                        } else {
                            orderDao.updateOrderStatus(openOrder.orderId, "PARTIALLY_FILLED")
                        }

                        if (openOrder.quantity == 0) {
                            orderDao.updateOrderStatus(openOrder.orderId, "FILLED")
                        } else {
                            orderDao.updateOrderStatus(openOrder.orderId, "PARTIALLY_FILLED")
                        }
                    }
                } catch (e: Exception) {
                    // Handle any exceptions, log errors for debugging
                    println("Error processing order match: ${e.message}")
                }
            }

            if (responseDTO.quantity > 0) {
                orderDao.updateOrderStatus(responseDTO.orderId, "OPEN")
            }
        }
    }


    fun getOrdersByUserId(userId: Int): List<OrderResponseDTO?> {
        return orderDao.getOrdersByUserId(userId)
    }

    fun getTransactionsByUserId(userId: Int): List<TransactionResponsetDTO?> {
        return transactionDao.getTransactionsByUser(userId)
    }
}
