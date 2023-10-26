package com.example.taskbuddy.ServiceProviderFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.taskbuddy.Modals.orderdetails
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ServiceOngoingFragment : Fragment() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var servnamel: TextView
    private lateinit var useridl: TextView
    private lateinit var odertimel: TextView
    private lateinit var totall: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_ongoing, container, false)

        servnamel = view.findViewById(R.id.serviceName)
        useridl = view.findViewById(R.id.JobDescription)
        odertimel = view.findViewById(R.id.orderTime)
        totall = view.findViewById(R.id.total)
        firebaseAuth = FirebaseAuth.getInstance()

        getorder()
        val finishedButton: Button = view.findViewById(R.id.finishedButton)

        finishedButton.setOnClickListener {
            val user = firebaseAuth.currentUser
            user?.let {
                val userId = it.uid
                dbRef = FirebaseDatabase.getInstance().getReference("orderdetails")

                // Query the database to find the specific order where service provider ID matches the current user ID
                dbRef.orderByChild("serviceprovider").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (jobSnapshot in snapshot.children) {
                                // Change the status to 'no'
                                jobSnapshot.ref.child("status").setValue("no").addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        // Navigate to ServiceHistoryFragment
                                        val transaction = parentFragmentManager.beginTransaction()
                                        transaction.replace(R.id.fragment_container, ServiceHistoryFragment())
                                        transaction.addToBackStack(null)
                                        transaction.commit()
                                    } else {
                                        // Handle status update failure if needed
                                    }
                                }
                            }
                        } else {
                            // Handle no jobs found for the service provider
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error
                    }
                })

                val cancelButton: Button = view.findViewById(R.id.cancelButton)

                cancelButton.setOnClickListener {
                    // Redirect to ServiceHomeFragment when Cancel button is clicked
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, ServiceHomeFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }


        return view
    }

    private fun getorder() {
        dbRef = FirebaseDatabase.getInstance().getReference("orderdetails")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (jobSnapshot in snapshot.children) {
                        val orderId = jobSnapshot.child("orderId").getValue(String::class.java) ?: ""
                        val orderTime = jobSnapshot.child("orderTime").getValue(String::class.java) ?: ""
                        val total = jobSnapshot.child("total").getValue(Double::class.java) ?: 0.0
                        // Retrieve servicesUsed list
                        val servicesUsedList = ArrayList<String>()
                        for (serviceSnapshot in jobSnapshot.child("servicesUsed").children) {
                            val service = serviceSnapshot.getValue(String::class.java) ?: ""
                            servicesUsedList.add(service)
                        }

                        // Now you can use these values to populate your TextViews
                        servnamel.text = servicesUsedList.joinToString(", ")
                        useridl.text = orderId
                        odertimel.text = orderTime
                        totall.text = total.toString()
                    }
                } else {
                    // Handle no jobs found in the orderdetails table
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }



}
