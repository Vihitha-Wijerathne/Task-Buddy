package com.example.taskbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.taskbuddy.Payment.AddCardBottomSheetFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openBottomSheetButton = findViewById<Button>(R.id.open_bottom_sheet_button)

        openBottomSheetButton.setOnClickListener {
            val bottomSheetFragment = AddCardBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}