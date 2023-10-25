package com.example.taskbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.taskbuddy.Modals.UserModal
import com.example.taskbuddy.WelcomePage.SignInPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernametxt: TextView
    private lateinit var email: TextView
    private lateinit var contactNumb: TextView
    private lateinit var nic: TextView
    private lateinit var signoutbtn: ImageView
    private lateinit var location: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        var userid = ""
        usernametxt = view.findViewById(R.id.username_profile)
        email = view.findViewById(R.id.email_profile)
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

            database = FirebaseDatabase.getInstance().getReference("users").child(userid)
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userresults = snapshot.getValue(UserModal::class.java)
                        nic.text = userresults?.nic
                        usernametxt.text = userresults?.name
                        email.text = userresults?.email
                        contactNumb.text = userresults?.number
                        location.text = userresults?.location
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