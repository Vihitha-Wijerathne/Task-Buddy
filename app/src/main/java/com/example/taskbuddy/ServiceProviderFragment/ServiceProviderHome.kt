package com.example.taskbuddy.ServiceProviderFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.taskbuddy.HistoryFragment
import com.example.taskbuddy.HomeFragment
import com.example.taskbuddy.R
import com.example.taskbuddy.UserFragment

class ServiceProviderHome : AppCompatActivity() {
    private lateinit var ser1 : ImageButton
    private lateinit var ser2: ImageButton
    private lateinit var ser3: ImageButton
    private lateinit var ser4: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_home)

        ser1 = findViewById(R.id.imageButton)
        ser2 = findViewById(R.id.imageButton5)
        ser3 = findViewById(R.id.imageButton6)
        ser4 = findViewById(R.id.imageButton7)

        val ser1Frag = ServiceHomeFragment()
        val ser2Frag = ServiceUserFragment()
        val ser3Frag = ServiceHistoryFragment()
        val ser4Frag = ServiceOngoingJobsFragment()

        ser1.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ser1Frag)
                commit()
            }
        }

        ser2.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ser2Frag)
                commit()
            }
        }

        ser3.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ser3Frag)
                commit()
            }
        }

        ser4.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ser4Frag)
                commit()
            }
        }
    }
}