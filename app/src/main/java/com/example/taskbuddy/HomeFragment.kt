package com.example.taskbuddy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.taskbuddy.Adapter.CreditCard
import com.example.taskbuddy.Feedback.FeedbackForm
import com.example.taskbuddy.Payment.ConfirmPaymentActivity
import com.example.taskbuddy.Search.PlumberSearch
import com.example.taskbuddy.SelectServices.AllServices
import com.example.taskbuddy.ServiceProviderFragment.ServiceProviderHome


class HomeFragment : Fragment() {
    private lateinit var cleanbtn : ImageView
    private lateinit var plumberbtn : ImageView
    private lateinit var electricalbtn : ImageView
    private lateinit var paintbtn : ImageView
    private lateinit var trainerbtn : ImageView
    private lateinit var petbtn : ImageView
    private lateinit var feedbackbtn : Button
    private lateinit var allservices : Button



    @SuppressLint("MissingInflatedId", "CutPasteId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        cleanbtn = view.findViewById(R.id.cleaninbtn)
        plumberbtn = view.findViewById(R.id.plumberbtn)
        petbtn = view.findViewById(R.id.petbtn)
        electricalbtn = view.findViewById(R.id.electricalbtn)
        paintbtn = view.findViewById(R.id.paintbtn)
        trainerbtn = view.findViewById(R.id.trainerbtn)
        allservices = view.findViewById(R.id.allservices )

        feedbackbtn = view.findViewById(R.id.feedback)

        trainerbtn.setOnClickListener {
            val intent = Intent(requireContext(), ConfirmPaymentActivity::class.java)
            startActivity(intent)
        }

        allservices.setOnClickListener {
            val intent = Intent(requireContext(), AllServices::class.java)
            startActivity(intent)
        }

        feedbackbtn.setOnClickListener {
            val intent = Intent(requireContext(), ServiceProviderHome::class.java)
            startActivity(intent)
        }

        plumberbtn.setOnClickListener {
            val intent = Intent(requireContext(), PlumberSearch::class.java)
            startActivity(intent)
        }

      val payment = view.findViewById<Button>(R.id.payment)
        payment.setOnClickListener {
            val CardDetailsFragment = CreditCard()
            CardDetailsFragment.show(
                requireActivity().supportFragmentManager,
                CardDetailsFragment.tag
            )
        }

      return view
    }

}