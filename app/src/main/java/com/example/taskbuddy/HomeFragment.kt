package com.example.taskbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.findViewTreeViewModelStoreOwner


class HomeFragment : Fragment() {
    private lateinit var cleanbtn : Button
    private lateinit var plumberbtn : Button
    private lateinit var electricalbtn : Button
    private lateinit var paintbtn : Button
    private lateinit var trainerbtn : Button
    private lateinit var petbtn : Button


    @SuppressLint("MissingInflatedId", "CutPasteId")
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








      return view
    }

}