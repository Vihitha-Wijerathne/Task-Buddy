package com.example.taskbuddy.Modals

data class AddFeedbackModal(
    val userId: String,  // The ID of the user providing the feedback
    val servicepId: String?,  // The ID of the service provider receiving the feedback
    val serviceRating: Float,  // The rating for the service (e.g., 1 to 5 stars)
    val timeManagementRating: Float,  // The rating for time management
    val userFriendlinessRating: Float,  // The rating for user-friendliness
    val overallSatisfactionRating: Float,  // The rating for overall satisfaction
    val comments: String  // User's comments
)

