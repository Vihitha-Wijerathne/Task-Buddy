package com.example.taskbuddy.Feedback

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.taskbuddy.HomeFragment
import com.example.taskbuddy.MainActivity
import com.example.taskbuddy.Modals.AddFeedbackModal
import com.example.taskbuddy.Modals.ServiceProviderModal
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FeedbackForm : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var serviceRatingBar: RatingBar
    private lateinit var timeManagementRatingBar: RatingBar
    private lateinit var userFriendlinessRatingBar: RatingBar
    private lateinit var overallSatisfactionRatingBar: RatingBar
    private lateinit var commentsEditText: EditText
    private lateinit var submitButton: Button
    private var serviceRating: Float = 0.0F
    private var timeManagementRating: Float = 0.0F
    private var userFriendlinessRating: Float = 0.0F
    private var overallSatisfactionRating: Float = 0.0F
    private var ratingAverage: Float = 0.0F
    private var jobRating: Float = 0.0F
    private var averageCount: Int = 0
    private lateinit var comments: String
    private  lateinit var serviceProviderId: String
    private  lateinit var orderId: String
    private lateinit var nic: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var number: String
    private lateinit var service: String
    private lateinit var location: String
    private lateinit var status: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)

        serviceProviderId = "57uPVPCipyc4353xo2yTlE0hE353"
        orderId = "-NhcdiLO68jFcgMtuX48"

        serviceRatingBar = findViewById(R.id.serviceRatingBar)
        timeManagementRatingBar = findViewById(R.id.timeManagementRatingBar)
        userFriendlinessRatingBar = findViewById(R.id.userFriendlinessRatingBar)
        overallSatisfactionRatingBar = findViewById(R.id.overallSatisfactionRatingBar)
        commentsEditText = findViewById(R.id.commentsEditText)
        submitButton = findViewById(R.id.submitButton)
        comments = commentsEditText.text.toString()

        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        val userId = user!!.uid
        var feedbackId = ""

        submitButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

//        submitButton.setOnClickListener {
//            database = FirebaseDatabase.getInstance().getReference("feedback")
//
//            feedbackId = database.push().key!!
//
//            val feedback =AddFeedbackModal(userId,serviceProviderId,orderId, serviceRating,timeManagementRating, userFriendlinessRating, overallSatisfactionRating, comments)
//
//            database.child(feedbackId).setValue(feedback)
//                .addOnCompleteListener {
//                    Toast.makeText(this, "feedback added Successfully", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener { err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
//                }
//
//            getServiceData()
//
//            jobRating = ((serviceRating + timeManagementRating + userFriendlinessRating + overallSatisfactionRating) / 4)
//
//            ratingAverage = ((ratingAverage * averageCount) + jobRating) / (averageCount + 1)
//
//            averageCount += 1
//
//            updateServiceProvider()
//
//        }
//
  }
//    private fun getServiceData(){
//        database = FirebaseDatabase.getInstance().getReference("serviceprovider").child(serviceProviderId)
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    val getServiceProvider = snapshot.getValue(ServiceProviderModal::class.java)
//                    ratingAverage = getServiceProvider?.rating!!
//                    averageCount = getServiceProvider?.count!!
//                    nic= getServiceProvider?.nic!!
//                    name = getServiceProvider?.name!!
//                   email = getServiceProvider?.email!!
//                    number = getServiceProvider?.number!!
//                   service = getServiceProvider?.service!!
//                   location = getServiceProvider?.location!!
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@FeedbackForm,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
//            }
//        })
//
//    }
//    private fun updateServiceProvider(){
//        database = FirebaseDatabase.getInstance().getReference("serviceprovider").child(serviceProviderId)
//
//        val updateServiceProvider = ServiceProviderModal(serviceProviderId,nic,name,email,number,service,location,ratingAverage,status,averageCount)
//        database.setValue(updateServiceProvider)
//    }

}
