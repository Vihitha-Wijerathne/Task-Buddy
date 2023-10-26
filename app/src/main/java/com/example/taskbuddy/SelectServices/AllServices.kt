package com.example.taskbuddy.SelectServices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.taskbuddy.R
import com.example.taskbuddy.Search.CleaningSearch
import com.example.taskbuddy.Search.ElectricalSearch
import com.example.taskbuddy.Search.PaintingSearch
import com.example.taskbuddy.Search.PlumberSearch

class AllServices : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allservices)

        val cleanbtn = findViewById<ImageView>(R.id.cleaningbtn)
        val plumberbtn = findViewById<ImageView>(R.id.plumberbtn)
        val electricalbtn = findViewById<ImageView>(R.id.electricalbtn)
        val paintbtn = findViewById<ImageView>(R.id.paintbtn)

        plumberbtn.setOnClickListener {
            val intent = Intent(this, PlumberSearch::class.java)
            startActivity(intent)
        }

        cleanbtn.setOnClickListener {
            val intent = Intent(this, CleaningSearch::class.java)
            startActivity(intent)
        }

        electricalbtn.setOnClickListener {
            val intent = Intent(this, ElectricalSearch::class.java)
            startActivity(intent)
        }

        paintbtn.setOnClickListener {
            val intent = Intent(this, PaintingSearch::class.java)
            startActivity(intent)
        }
    }
}
