package com.oceanx.myorders.model

data class Order(
    val id: String,
    val vehicleType: String,
    val dateTime: String,
    val orderId: String,
    val pickupLocation: String,
    val dropLocation: String,
    val amount: Double,
    val status: OrderStatus
)

enum class OrderStatus {
    COMPLETED,
    CANCELLED,
    BOOKED_AGAIN
}
