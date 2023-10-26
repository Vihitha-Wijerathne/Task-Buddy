package com.example.taskbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.Adaptors.HistoryAdaptor
import com.example.taskbuddy.Adaptors.PlumberAdaptor
import com.example.taskbuddy.Modals.ServiceProviderModal
import com.example.taskbuddy.Modals.orderdetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
    private lateinit var orderlist: ArrayList<orderdetails>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var plumberRecyclerView: RecyclerView
    private lateinit var plumberAdapter: HistoryAdaptor
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_history, container, false)

        orderlist = ArrayList()

        plumberRecyclerView = view.findViewById(R.id.orderhistoryrecyclerview)
        plumberRecyclerView.layoutManager = LinearLayoutManager(context)
        plumberAdapter = HistoryAdaptor(requireContext(),orderlist)
        plumberRecyclerView.adapter = plumberAdapter

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid

        dbRef = FirebaseDatabase.getInstance().getReference("orderdetails")

        dbRef.orderByChild("status")
            .equalTo("no")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    orderlist.clear()
                    for (postSnapshot in snapshot.children) {
                        val order = postSnapshot.getValue(orderdetails::class.java)
                        if (order != null) {
                            if(userId == order.userId){
                            orderlist.add(order)
                            }
                        }
                    }
                    plumberAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        return view
    }

}