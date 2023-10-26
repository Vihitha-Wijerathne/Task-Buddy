package com.example.taskbuddy.SelectServices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.taskbuddy.Adapter.CreditCard
import com.example.taskbuddy.Modals.orderdetails
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class ElectricalServiceSelect : AppCompatActivity() {
    private var serviceprovider: String? = null
    private  var slocation: String? = null
    private  var srating: Int = 0
    private var totalpayment: Double = 0.0
    private  var snumber: String? = null
    private lateinit var emergencyrepaires: CheckBox
    private lateinit var leakrepaire: CheckBox
    private lateinit var pipeinstallation: CheckBox
    private lateinit var draincleaning: CheckBox
    private lateinit var barthroomplumbing: CheckBox
    private lateinit var kitachenplumbing: CheckBox
    private lateinit var proccedbtn: Button
    private lateinit var sname: TextView
    private lateinit var location: TextView
    private lateinit var phonenumber: TextView
    private lateinit var rating: TextView
    private lateinit var payableamount: TextView
    private lateinit var dbref: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var orderhistory: ArrayList<String> = ArrayList()
    private lateinit var time: String
    private var servicepId: String? = null
    private var emergencycost: Double = 2000.0
    private var leakcost: Double = 1500.0
    private var pipecost: Double = 4000.0
    private var draincost: Double = 1000.0
    private var barthroomcost: Double = 1100.0
    private var kitachencost: Double = 2500.0
    private var totalAmount: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electrical_service_select)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        orderhistory = ArrayList()

        time = "$hour.$minute"

        orderhistory = ArrayList()

        emergencyrepaires = findViewById(R.id.emergencyrepaire)
        leakrepaire = findViewById(R.id.leakrepaire)
        pipeinstallation = findViewById(R.id.pipeinstallation)
        draincleaning = findViewById(R.id.driancleaning)
        barthroomplumbing = findViewById(R.id.barthroomplumb)
        kitachenplumbing = findViewById(R.id.kitchenplumb)
        proccedbtn = findViewById(R.id.proceedbtn)
        sname = findViewById(R.id.serviceprovidername)
        location = findViewById(R.id.serviceproviderlocation)
        phonenumber = findViewById(R.id.serviceprovidernumber)
        rating = findViewById(R.id.serviceproviderrating)
        payableamount = findViewById(R.id.payableamount)

        serviceprovider = intent.getStringExtra("name")
        slocation = intent.getStringExtra("location")
        snumber = intent.getStringExtra("number")
        srating = intent.getIntExtra("rating",0)
        servicepId = intent.getStringExtra("id")

        sname.text = serviceprovider
        location.text = slocation
        phonenumber.text = snumber
        rating.text = srating.toString()

        emergencyrepaires.text = "Electrical Repairs ( $emergencycost)"
        leakrepaire.text = "Electrical Installations ($leakcost)"
        pipeinstallation.text = "Circuit Breaker Panel Upgrades ($pipecost)"
        draincleaning.text = "Wiring and Rewiring ($draincost)"
        barthroomplumbing.text = "Electrical Inspections ($barthroomcost)"
        kitachenplumbing.text = "Emergency Electrical Services ($kitachencost)"

        emergencyrepaires.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        leakrepaire.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        pipeinstallation.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        draincleaning.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        barthroomplumbing.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        kitachenplumbing.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotalAmount()
            updatePayableAmount()
        }

        firebaseAuth = FirebaseAuth.getInstance()




        proccedbtn.setOnClickListener{
            setorderdetails()
            val intent = Intent(this, CreditCard::class.java)
            startActivity(intent)
        }
    }

    private fun calculateTotalAmount() {
        totalAmount = 0.0
        orderhistory.clear()

        if (emergencyrepaires.isChecked) {
            totalAmount += emergencycost
            orderhistory.add("Electrical Repairs")
        }

        if (leakrepaire.isChecked) {
            totalAmount += leakcost
            orderhistory.add("Electrical Installations")
        }

        if (pipeinstallation.isChecked) {
            totalAmount += pipecost
            orderhistory.add("Circuit Breaker Panel Upgrades")
        }

        if (draincleaning.isChecked) {
            totalAmount += draincost
            orderhistory.add("Wiring and Rewiring")
        }

        if (barthroomplumbing.isChecked) {
            totalAmount += barthroomcost
            orderhistory.add("Electrical Inspections")
        }

        if (kitachenplumbing.isChecked) {
            totalAmount += kitachencost
            orderhistory.add("Emergency Electrical Services")
        }
    }

    private fun updatePayableAmount() {
        payableamount.text = totalAmount.toString()
    }


    private fun setorderdetails() {
        val user = firebaseAuth.currentUser
        val userid = user!!.uid
        var orderid = ""
        dbref = FirebaseDatabase.getInstance().getReference("orderdetails")

        orderid = dbref.push().key!!
        val servicesUsed = orderhistory.toList()


        val order = orderdetails(orderid,userid,servicesUsed,time,servicepId,totalAmount)


        dbref.child(orderid).setValue(order)
            .addOnSuccessListener {
                Toast.makeText(this, "Order added Successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, CreditCard::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
    }
}