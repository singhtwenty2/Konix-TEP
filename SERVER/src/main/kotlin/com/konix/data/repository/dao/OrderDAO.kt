package com.konix.data.repository.dao

import com.konix.data.dto.request.OrderRequestDTO
import com.konix.data.dto.request.enums.OrderStatus
import com.konix.data.dto.response.OrderResponseDTO
import com.konix.data.repository.entity.Orders
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object OrderDAO {

    fun createOrder(requestDTO: OrderRequestDTO): OrderResponseDTO {
        var orderId: Int? = null
        transaction {
            // Insert the order into the database
            Orders.insert {
                it[userId] = requestDTO.userId
                it[companyId] = requestDTO.companyId
                it[orderType] = requestDTO.orderType.name
                it[quantity] = requestDTO.quantity
                it[price] = requestDTO.price.toBigDecimal()
                it[orderStatus] = requestDTO.orderStatus.name
                it[createdAt] = System.currentTimeMillis().toString()
                it[updatedAt] = System.currentTimeMillis().toString()
            }

            // Retrieve the last inserted ID for the given userId
            orderId = Orders.select { Orders.userId eq requestDTO.userId }.map { it[Orders.id] }.last()
        }

        return getOrderById(orderId!!)!!
    }

    private fun getOrderById(orderId: Int): OrderResponseDTO? {
        return transaction {
            Orders.select { Orders.id eq orderId }.map {
                OrderResponseDTO(
                    orderId = it[Orders.id],
                    userId = it[Orders.userId],
                    companyId = it[Orders.companyId],
                    orderType = it[Orders.orderType],
                    quantity = it[Orders.quantity],
                    price = it[Orders.price].toDouble(),
                    orderStatus = it[Orders.orderStatus],
                    createdAt = it[Orders.createdAt],
                    updatedAt = it[Orders.updatedAt]
                )
            }.singleOrNull()
        }
    }

    fun getOpenOrdersByCompany(companyId: Int): List<OrderResponseDTO> {
        return transaction {
            Orders.select { (Orders.companyId eq companyId) and (Orders.orderStatus eq OrderStatus.OPEN.name) }.map {
                OrderResponseDTO(
                    orderId = it[Orders.id],
                    userId = it[Orders.userId],
                    companyId = it[Orders.companyId],
                    orderType = it[Orders.orderType],
                    quantity = it[Orders.quantity],
                    price = it[Orders.price].toDouble(),
                    orderStatus = it[Orders.orderStatus],
                    createdAt = it[Orders.createdAt],
                    updatedAt = it[Orders.updatedAt]
                )
            }
        }
    }

    fun updateOrderStatus(
        orderId: Int,
        status: String
    ) {
        return transaction {
            Orders.update({ Orders.id eq orderId }) {
                it[orderStatus] = status
                it[updatedAt] = System.currentTimeMillis().toString()
            }
        }
    }

    fun getOrdersByUserId(userId: Int): List<OrderResponseDTO?> {
        return transaction {
            Orders.select { Orders.userId eq userId }.map {
                OrderResponseDTO(
                    orderId = it[Orders.id],
                    userId = it[Orders.userId],
                    companyId = it[Orders.companyId],
                    orderType = it[Orders.orderType],
                    quantity = it[Orders.quantity],
                    price = it[Orders.price].toDouble(),
                    orderStatus = it[Orders.orderStatus],
                    createdAt = it[Orders.createdAt],
                    updatedAt = it[Orders.updatedAt]
                )
            }
        }
    }

    fun deleteOrder(
        orderId: Int,
        userId: Int,
        companyId: Int
    ) {
        return transaction {
            Orders.deleteWhere { (id eq orderId) and (Orders.userId eq userId) and (Orders.companyId eq companyId) }
        }
    }
}
