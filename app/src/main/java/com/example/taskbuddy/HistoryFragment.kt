package com.example.taskbuddy

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
    private lateinit var plumberResult: ArrayList<orderdetails>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var plumberRecyclerView: RecyclerView
    private lateinit var plumberAdapter: HistoryAdaptor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_history, container, false)

        plumberResult = ArrayList()

        plumberRecyclerView = view.findViewById(R.id.plumberrecyclerview)
        plumberRecyclerView.layoutManager = LinearLayoutManager(this)
        plumberAdapter = HistoryAdaptor(requireContext(), plumberResult)
        plumberRecyclerView.adapter = plumberAdapter

        firebaseAuth = FirebaseAuth.getInstance()

        dbRef = FirebaseDatabase.getInstance().getReference("orderdetails")

        dbRef.orderByChild("service").equalTo("Plumber")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    plumberResult.clear()
                    for (snapshot in dataSnapshot.children) {
                        val item = snapshot.getValue(ServiceProviderModal::class.java)
                        item?.let {
                            if (ulocation == item.location) {
                                plumberResult.add(item)
                            }
                        }
                    }


                    plumberAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("PlumberSearch", "Database error: ${error.message}")
                    Toast.makeText(this@PlumberSearch, "There is a problem retrieving data from the database", Toast.LENGTH_LONG).show()
                }
            })
        return view
    }

}