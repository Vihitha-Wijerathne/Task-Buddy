package com.example.taskbuddy.ServiceProviderFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.taskbuddy.Modals.ServiceProviderModal
import com.example.taskbuddy.Modals.UserModal
import com.example.taskbuddy.R
import com.example.taskbuddy.WelcomePage.SignInPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ServiceUserFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernametxt: TextView
    private lateinit var email: TextView
    private lateinit var contactNumb: TextView
    private lateinit var nic: TextView
    private lateinit var signoutbtn: ImageView
    private lateinit var location: TextView
    private lateinit var rating: TextView
    private lateinit var count: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_service_user, container, false)
        var userid = ""
        usernametxt = view.findViewById(R.id.username_profile)
        email = view.findViewById(R.id.email_profile)
        contactNumb = view.findViewById(R.id.number_profile)
        nic = view.findViewById(R.id.nic_profile)
        signoutbtn = view.findViewById(R.id.signoutbtn)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        signoutbtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, SignInPage::class.java)
            startActivity(intent)
            activity?.finish()
        }

        user?.let{
            userid = it.uid

            database = FirebaseDatabase.getInstance().getReference("serviceprovider").child(userid)
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userresults = snapshot.getValue(ServiceProviderModal::class.java)
                        nic.text = userresults?.nic
                        usernametxt.text = userresults?.name
                        email.text = userresults?.email
                        contactNumb.text = userresults?.number
                        location.text = userresults?.location
                        rating.text = userresults?.rating.toString()
                        count.text = userresults?.count.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
                }
            })

        }


        return view
    }

}