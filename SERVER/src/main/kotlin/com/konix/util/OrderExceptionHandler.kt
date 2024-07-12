package com.konix.util

class InsufficientBalanceException(message: String) : Exception(message)
class DematAccountNotFoundException(message: String) : Exception(message)
class OrderNotFoundException(message: String) : Exception(message)
class OrderAlreadyExistsException(message: String) : Exception(message)
class OrderStatusChangeException(message: String) : Exception(message)
class OrderTypeChangeException(message: String) : Exception(message)
class OrderQuantityChangeException(message: String) : Exception(message)
class OrderPriceChangeException(message: String) : Exception(message)
class OrderCompanyChangeException(message: String) : Exception(message)
class OrderUserChangeException(message: String) : Exception(message)
class OrderCreationException(message: String) : Exception(message)
class OrderUpdateException(message: String) : Exception(message)
class OrderDeletionException(message: String) : Exception(message)