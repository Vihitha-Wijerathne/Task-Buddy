package com.example.taskbuddy.Modals

data class AddFeedbackModal(
    val userId: String,
    val serviceProviderId: String?,
    val orderId: String?,
    val serviceRating: Float,
    val timeManagementRating: Float,
    val userFriendlinessRating: Float,
    val overallSatisfactionRating: Float,
    val comments: String
)

