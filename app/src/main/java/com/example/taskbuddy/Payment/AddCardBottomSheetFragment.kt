package com.example.taskbuddy.Payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskbuddy.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCardBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_add_credit_card, container, false)
        // Place your code for initializing views and handling interactions here, just like in AddCardDetails.
        return view
    }
}
