package com.example.taskbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var homefr : ImageButton
    private lateinit var userfr: ImageButton
    private lateinit var histtryfr: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homefr = findViewById(R.id.imageButton2)
        userfr = findViewById(R.id.imageButton3)
        histtryfr = findViewById(R.id.imageButton4)

        val homeFrag = HomeFragment()
        val userFrag = UserFragment()
        val historyFrag = HistoryFragment()

        homefr.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, homeFrag)
                commit()
            }
        }

        userfr.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, userFrag)
                commit()
            }
        }

        histtryfr.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, historyFrag)
                commit()
            }
        }

    }
}