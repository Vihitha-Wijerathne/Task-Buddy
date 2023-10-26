package com.example.taskbuddy.ServiceProviderFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.Modals.orderdetails
import com.example.taskbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ServiceHistoryFragment : Fragment() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderDetailsAdapter
    private var orderDetailsList: MutableList<orderdetails> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderDetailsAdapter(orderDetailsList)
        recyclerView.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("orderdetails")
            dbRef.orderByChild("serviceprovider")
                .equalTo(userId)
                .orderByChild("status")
                .equalTo("no")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (orderSnapshot in snapshot.children) {
                                val orderDetail = orderSnapshot.getValue(orderdetails::class.java)
                                if (orderDetail != null) {
                                    orderDetailsList.add(orderDetail)
                                }
                            }
                            adapter.notifyDataSetChanged()
                        } else {
                            // Handle no orders found for the service provider
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error
                    }
                })
        }

        return view
    }
}

class OrderDetailsAdapter(private val orderDetailsList: List<orderdetails>) :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceName: TextView = itemView.findViewById(R.id.serviceNameTextView)
        val userId: TextView = itemView.findViewById(R.id.userIdTextView)
        val orderTime: TextView = itemView.findViewById(R.id.orderTimeTextView)
        val total: TextView = itemView.findViewById(R.id.totalTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.orderdetails, parent, false) // Corrected layout here
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderDetail = orderDetailsList[position]
        holder.serviceName.text = orderDetail.servicesUsed.joinToString(", ")
        holder.userId.text = orderDetail.userId
        holder.orderTime.text = orderDetail.orderTime
        holder.total.text = orderDetail.total.toString()
    }

    override fun getItemCount(): Int {
        return orderDetailsList.size
    }
}