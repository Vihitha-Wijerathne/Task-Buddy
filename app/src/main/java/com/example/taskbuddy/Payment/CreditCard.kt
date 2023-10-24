package com.example.taskbuddy.Payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.Adapter.CreditCardAdapter
import com.example.taskbuddy.Modals.AddCardModal
import com.example.taskbuddy.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CreditCard : BottomSheetDialogFragment() {
    private lateinit var creditCards: ArrayList<AddCardModal>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var recordAdapter: CreditCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_credit_cards, container, false)

        creditCards = ArrayList()
        recordAdapter = CreditCardAdapter(creditCards, this::deleteItem)
        recordRecyclerView = view.findViewById(R.id.creditCardRecyclerView)
        recordRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recordRecyclerView.adapter = recordAdapter

        getCreditCards()

        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val addCardDetailsFragment = AddCardDetails()
            addCardDetailsFragment.show(requireActivity().supportFragmentManager, addCardDetailsFragment.tag)
        }

        return view
    }

    private fun getCreditCards() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("CreditCards")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    creditCards.clear()
                    if (snapshot.exists()) {
                        for (cbResultsSnap in snapshot.children) {
                            val cbResults = cbResultsSnap.getValue(AddCardModal::class.java)
                            if (cbResults != null && cbResults.userId == userId) {
                                // Add new items at the beginning of the list
                                creditCards.add(0, cbResults)
                            }
                        }
                        recordAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the database error here
                    Log.e("DatabaseError", "Error fetching credit cards: ${error.message}")
                }
            })
        }
    }

    private fun deleteItem(card: AddCardModal) {
        val cardNumber = card.cardId
        if (cardNumber != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("CreditCards").child(cardNumber)
            dbRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Deletion was successful in the database
                    // Dismiss the bottom sheet
                    dismiss()
                } else {
                    // Handle the database deletion error
                    Log.e("DatabaseDeletionError", "Error deleting item: ${task.exception?.message}")
                }
            }
        }
    }
}
