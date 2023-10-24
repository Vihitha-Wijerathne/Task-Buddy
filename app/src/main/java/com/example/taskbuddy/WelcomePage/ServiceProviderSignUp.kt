package com.example.taskbuddy.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.taskbuddy.Modals.ServiceProviderModal
import com.example.taskbuddy.Modals.UserModal
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ServiceProviderSignUp : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var registerbtn: Button
    private lateinit var alredyhv: TextView
    private lateinit var rname: EditText
    private lateinit var rnic: EditText
    private lateinit var rpnumber: EditText
    private lateinit var remail: EditText
    private lateinit var rpasswd: EditText
    private lateinit var repasswd: EditText
    private lateinit var rlocation: EditText
    private lateinit var servicetype: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_sign_up)

        registerbtn = findViewById(R.id.reg_button)
        alredyhv = findViewById(R.id.already_acc)
        rname = findViewById(R.id.p_name)
        rnic = findViewById(R.id.reg_nic)
        rpnumber = findViewById(R.id.reg_phone)
        remail = findViewById(R.id.reg_email)
        rpasswd = findViewById(R.id.reg_password)
        repasswd = findViewById(R.id.re_password)
        rlocation = findViewById(R.id.reg_location)
        servicetype = findViewById(R.id.spiiner1)

        val types = arrayOf("Plumber","Electrician","Cleaner","Care Taker","Maid")
        val arrayAdp = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,types)
        servicetype.adapter = arrayAdp

        servicetype?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@ServiceProviderSignUp,"Item is ${types[p2]}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@ServiceProviderSignUp,"Nothing is selected", Toast.LENGTH_LONG).show()
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("serviceprovider")

        alredyhv.setOnClickListener{
            val intent = Intent(this,SignInPage::class.java)
            startActivity(intent)
        }

        registerbtn.setOnClickListener{
            val name = rname.text.toString()
            val nic = rnic.text.toString()
            val number = rpnumber.text.toString()
            val email = remail.text.toString()
            val location = rlocation.text.toString()
            val password = rpasswd.text.toString()
            val repassword = repasswd.text.toString()
            val servicet = servicetype.selectedItem.toString()
            val rating = 0
            val status = "yes"


            if(nic.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()){
                if(email.contains("@")){
                    if(password.length>=6){
                        if(nic.length.equals(12)){
                            if(password.equals(repassword)) {

                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {

                                            fun writeNewUser(
                                                userId: String,
                                                nic: String,
                                                name: String,
                                                email: String,
                                                phoneNumb: String,
                                                location: String,
                                                servicetype: String,
                                                rate: Int,
                                                state: String
                                            ) {
                                                val user = ServiceProviderModal(nic, name, email, phoneNumb,servicetype, location,rate,state)
                                                database.child(userId).setValue(user)
                                            }

                                            val user = firebaseAuth.currentUser
                                            user?.let {
                                                val uid = it.uid

                                                writeNewUser(uid, nic, name, email, number,servicet, location, rating,status)

                                                user?.sendEmailVerification()
                                                    ?.addOnCompleteListener(this) { task ->
                                                        if (task.isSuccessful) {
                                                            Toast.makeText(
                                                                this,
                                                                "Verification email sent to ${user.email}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                this,
                                                                "Failed to send verification email.",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                            }

                                            val intent =
                                                Intent(this, SignInPage::class.java)
                                            startActivity(intent)


                                        } else {
                                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                            }else{
                                Toast.makeText(this,"Passwords are not matching", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(this,"NIC must have 12 numbers", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(this,"Password must have 6 characters or more", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this,"email must contain @ sign", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this,"NIC,emal and password must be filled", Toast.LENGTH_LONG).show()
            }



        }
    }
}