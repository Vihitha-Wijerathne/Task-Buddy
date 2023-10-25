package com.example.taskbuddy.Modals

data class orderdetails(
    val userId: String,
    val servicesUsed: List<String>,
    val orderTime: String,
    val serviceprovider: String?
)
