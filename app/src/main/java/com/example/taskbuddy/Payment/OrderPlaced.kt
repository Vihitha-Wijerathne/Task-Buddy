package com.example.taskbuddy.Payment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.taskbuddy.Feedback.FeedbackForm
import com.example.taskbuddy.MainActivity
import com.example.taskbuddy.R

class OrderPlaced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        val homepage = findViewById<Button>(R.id.homepage)
        homepage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
