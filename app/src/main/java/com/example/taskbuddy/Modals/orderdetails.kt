package com.example.taskbuddy.Modals

data class orderdetails(
    val orderId: String = "",
    val userId: String = "",
    val servicesUsed: List<String> = emptyList(),
    val orderTime: String = "",
    val serviceprovider: String? = null,
    val total: Double = 0.0,
    val status: String = ""
)

