package com.konix.service.order

import com.konix.data.dto.request.OrderRequestDTO
import com.konix.data.dto.request.TransactionRequestDTO
import com.konix.data.dto.request.enums.OrderStatus
import com.konix.data.dto.request.enums.OrderType
import com.konix.data.dto.response.DematAccountResponseDTO
import com.konix.data.dto.response.OrderResponseDTO
import com.konix.data.dto.response.TransactionResponsetDTO
import com.konix.data.repository.dao.DematAccountDAO
import com.konix.data.repository.dao.OrderDAO
import com.konix.data.repository.dao.PortfolioDAO
import com.konix.data.repository.dao.TransactionDAO
import com.konix.util.DematAccountNotFoundException
import com.konix.util.InsufficientBalanceException
import com.konix.util.InsufficientStockException
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.min

class OrderService {

    private val orderDao = OrderDAO
    private val transactionDao = TransactionDAO
    private val dematAccountDao = DematAccountDAO
    private val portfolioDao = PortfolioDAO

    fun placeOrder(userId: Int, orderRequestDTO: OrderRequestDTO): OrderResponseDTO {
        val dematAccount = dematAccountDao.getDematAccountDetails(userId)
            ?: throw DematAccountNotFoundException("Demat Account Not Found")

        return transaction {
            // Check for sufficient balance for BUY orders
            if (orderRequestDTO.orderType == OrderType.BUY && dematAccount.balance < (orderRequestDTO.price * orderRequestDTO.quantity)) {
                throw InsufficientBalanceException("Insufficient Balance")
            }

            // Handle SELL orders
            if (orderRequestDTO.orderType == OrderType.SELL) {
                checkStockQuantity(userId, orderRequestDTO)
            }

            // Place order
            val createOrder = orderDao.createOrder(
                userId = userId,
                requestDTO = orderRequestDTO
            )

            // Update balance
            updateBalance(userId, orderRequestDTO, dematAccount)

            // Update portfolio
            updatePortfolio(userId, orderRequestDTO)

            // Match orders
            matchOrders(createOrder)

            createOrder
        }
    }

    private fun checkStockQuantity(userId: Int, orderRequestDTO: OrderRequestDTO) {
        val currentStockQuantity = portfolioDao.getStockQuantity(userId, orderRequestDTO.companyId)
        val requestedStockQuantity = orderRequestDTO.quantity

        if (currentStockQuantity < requestedStockQuantity) {
            throw InsufficientStockException("Insufficient Stocks")
        }
    }

    private fun updatePortfolio(userId: Int, orderRequestDTO: OrderRequestDTO) {
        val stockQuantity = portfolioDao.getStockQuantity(userId, orderRequestDTO.companyId)
        val newQuantity = when (orderRequestDTO.orderType) {
            OrderType.BUY -> stockQuantity + orderRequestDTO.quantity
            OrderType.SELL -> stockQuantity - orderRequestDTO.quantity
        }
        portfolioDao.updateStockQuantity(userId, orderRequestDTO.companyId, newQuantity)
    }

    private fun updateBalance(userId: Int, orderRequestDTO: OrderRequestDTO, dematAccount: DematAccountResponseDTO) {
        val totalAmount = orderRequestDTO.price * orderRequestDTO.quantity
        val newBalance = when (orderRequestDTO.orderType) {
            OrderType.BUY -> dematAccount.balance - totalAmount
            OrderType.SELL -> dematAccount.balance + totalAmount
        }
        dematAccountDao.updateBalance(userId, newBalance.toBigDecimal())
    }

    private fun matchOrders(newOrder: OrderResponseDTO) {
        val oppositeOrders = orderDao.getOpenOrdersByCompany(newOrder.companyId)

        transaction {
            for (oppositeOrder in oppositeOrders) {
                if (newOrder.quantity <= 0) break

                if (
                    ((newOrder.orderType == OrderType.BUY.name) && (oppositeOrder.orderType == OrderType.SELL.name) && (newOrder.price >= oppositeOrder.price)) ||
                    ((newOrder.orderType == OrderType.SELL.name) && (oppositeOrder.orderType == OrderType.BUY.name) && (newOrder.price <= oppositeOrder.price))
                ) {
                    val matchQuantity = min(newOrder.quantity, oppositeOrder.quantity)

                    // Create transaction
                    transactionDao.createTransaction(
                        TransactionRequestDTO(
                            orderId = newOrder.orderId,
                            userId = newOrder.userId,
                            companyId = newOrder.companyId,
                            quantity = matchQuantity,
                            price = oppositeOrder.price
                        )
                    )

                    // Update quantities
                    newOrder.quantity -= matchQuantity
                    oppositeOrder.quantity -= matchQuantity

                    // Update statuses
                    if (oppositeOrder.quantity == 0) {
                        orderDao.updateOrderStatus(oppositeOrder.orderId, OrderStatus.FILLED.name)
                    } else {
                        orderDao.updateOrderStatus(oppositeOrder.orderId, OrderStatus.PARTIALLY_FILLED.name)
                    }

                    if (newOrder.quantity == 0) {
                        orderDao.updateOrderStatus(newOrder.orderId, OrderStatus.FILLED.name)
                        break
                    } else {
                        orderDao.updateOrderStatus(newOrder.orderId, OrderStatus.PARTIALLY_FILLED.name)
                    }
                }
            }

            // If no match found, keep newOrder as OPEN
            if (newOrder.quantity > 0 && newOrder.orderStatus != OrderStatus.PARTIALLY_FILLED.name) {
                orderDao.updateOrderStatus(newOrder.orderId, OrderStatus.OPEN.name)
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

