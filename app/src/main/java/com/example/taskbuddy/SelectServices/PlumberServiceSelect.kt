package com.example.taskbuddy.SelectServices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskbuddy.R

class PlumberServiceSelect : AppCompatActivity() {
    private var serviceprovider: String? = null
    private  var slocation: String? = null
    private  var srating: Int = 0
    private  var snumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plumber_service_select)

        serviceprovider = intent.getStringExtra("name")
        slocation = intent.getStringExtra("location")
        snumber = intent.getStringExtra("number")
        srating = intent.getIntExtra("rating",0)




    }
}