package com.example.taskbuddy.ServiceProviderFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_ongoing, container, false)

        servnamel = view.findViewById(R.id.serviceName)
        useridl = view.findViewById(R.id.JobDescription)
        odertimel = view.findViewById(R.id.orderTime)
        firebaseAuth = FirebaseAuth.getInstance()

        getorder()

        return view
    }

    private fun getorder() {
        val user = firebaseAuth.currentUser
        user?.let {
            val userid = it.uid
            dbRef = FirebaseDatabase.getInstance().getReference("orderdetails").child(userid)
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val orderresults = snapshot.getValue(orderdetails::class.java)
                        if (orderresults != null) {
                            val servnameList = orderresults.servicesUsed ?: emptyList()
                            val userID = orderresults.userId
                            val odertime = orderresults.orderTime

                            // Assuming servnameList is a list of strings
                            servnamel.text = servnameList.joinToString(", ")
                            useridl.text = userID
                            odertimel.text = odertime
                        } else {
                            // Handle unexpected data structure
                        }
                    } else {
                        // Handle no data case
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }
}
