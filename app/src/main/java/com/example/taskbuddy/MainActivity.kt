package com.example.taskbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.taskbuddy.Payment.AddCardDetails
import com.example.taskbuddy.Payment.CreditCard
import com.example.taskbuddy.WelcomePage.ServiceProviderSignUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openBottomSheetButton = findViewById<Button>(R.id.open_bottom_sheet_button)

        openBottomSheetButton.setOnClickListener {
            val bottomSheetFragment = AddCardDetails()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        val openBottomSheetButton2 = findViewById<Button>(R.id.open_bottom_sheet_button2)

        openBottomSheetButton2.setOnClickListener {
            val bottomSheetFragment2 = CreditCard()
            bottomSheetFragment2.show(supportFragmentManager, bottomSheetFragment2.tag)
        }



    }
}