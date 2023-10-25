package com.example.taskbuddy.Feedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import com.example.taskbuddy.Modals.AddFeedbackModal
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var firebaseDatabase: FirebaseDatabase

class FeedbackForm : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var titleTextView: TextView
    private lateinit var instructionsTextView: TextView
//    private lateinit var serviceRatingBar: RatingBar
    private lateinit var commentsEditText: EditText
    private lateinit var submitButton: Button
    private var serviceRating: Float = 0.0F
    private lateinit var comments: String


//    private var uloandue: Double = 0.0
//    private lateinit var rechargebtn: Button
//    private var rechargeamount: Double = 0.0
//    private lateinit var inputamouhnt: EditText
//    private lateinit var inputstring: String
//    private lateinit var nic: String
//    private lateinit var name: String
//    private lateinit var email: String
//    private lateinit var number: String
//    private lateinit var disbalance: TextView
//    private lateinit var disloan: TextView
//    private var loana: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)


        titleTextView = findViewById(R.id.titleTextView)
        instructionsTextView = findViewById(R.id.instructionsTextView)
//        serviceRatingBar = findViewById(R.id.serviceRatingBar)
        // Find other RatingBars for different categories
        commentsEditText = findViewById(R.id.commentsEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
//            serviceRating = serviceRatingBar.rating
            // Retrieve ratings for other categories similarly
            comments = commentsEditText.text.toString()

            val feedbackData = AddFeedbackModal(comments)
            val database = FirebaseDatabase.getInstance().getReference("feedback")

            val feedbackId = database.push().key // Generate a unique key
            if (feedbackId != null) {
                database.child(feedbackId).setValue(feedbackData)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Feedback successfully added to Firebase
                            // You can navigate to another activity or show a success message here
                        } else {
                            // Handle the error here
                            // You can show an error message to the user
                        }
                    }
            }
        }
    }
//    private fun getUserId(): String {
//
//        firebaseAuth = fire
//        val user = firebaseAuth.currentUser
//        val userId = ""
//        user?.let{
//            userId = it.uid
//
//        }
//    }
//    private fun getServiceProvierId(): String {
//        // Implement the logic to retrieve the service provider's ID
//        // This might involve a database query or other method
//        // Return the service provider's ID once you have it
//    }
}
