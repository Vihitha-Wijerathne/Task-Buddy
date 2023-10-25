package com.example.taskbuddy.Adapter
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.Adapter.CreditCardAdapter
import com.example.taskbuddy.MainActivity
import com.example.taskbuddy.Modals.AddCardModal
import com.example.taskbuddy.Payment.AddCardDetails
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

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_credit_cards, container, false)

        creditCards = ArrayList()
        recordAdapter = CreditCardAdapter(creditCards, this::deleteItem, this::onUpdateCard, this::onSelectCard)
        recordRecyclerView = view.findViewById(R.id.creditCardRecyclerView)
        recordRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recordRecyclerView.adapter = recordAdapter

        getCreditCards()

        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val addCardDetailsFragment = AddCardDetails()
            addCardDetailsFragment.show(
                requireActivity().supportFragmentManager,
                addCardDetailsFragment.tag
            )
        }

        val proceedPaymentButton = view.findViewById<Button>(R.id.proceed_payment)

        // Set an OnClickListener to navigate to MainActivity when clicked
        proceedPaymentButton.setOnClickListener {
            // Check if a card is selected
            if (selectedPosition != RecyclerView.NO_POSITION) {
                // Card is selected, navigate to MainActivity
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            } else {
                // No card is selected, show a message or handle it accordingly
                Toast.makeText(requireContext(), "Please select a card to proceed.", Toast.LENGTH_SHORT).show()
            }
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
                                creditCards.add(cbResults)
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
                    dismiss()
                } else {
                    // Handle the database deletion error
                    Log.e(
                        "DatabaseDeletionError",
                        "Error deleting item: ${task.exception?.message}"
                    )
                }
            }
        }
    }

    private fun onSelectCard(selectedCard: AddCardModal) {
        val position = creditCards.indexOf(selectedCard)

        if (position != -1) {
            // Toggle the isSelected property
            val card = creditCards[position]
            card.isSelected = !card.isSelected

            // If a card is selected, update the selected position and highlight/unhighlight it
            if (card.isSelected) {
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    // Unhighlight the previously selected card
                    val previouslySelectedCard = creditCards[selectedPosition]
                    previouslySelectedCard.isSelected = false
                    recordAdapter.notifyItemChanged(selectedPosition)
                }
                // Update the selected position
                selectedPosition = position
            }

            // Highlight or unhighlight the card based on the isSelected property
            recordAdapter.notifyItemChanged(position)

            // Enable or disable the "Proceed Payment" button based on card selection
            val proceedPaymentButton = view?.findViewById<Button>(R.id.proceed_payment)
            proceedPaymentButton?.isEnabled = card.isSelected
        }
    }
    private fun onUpdateCard(card: AddCardModal) {
        openUpdateDialog(card)
    }

    private fun openUpdateDialog(card: AddCardModal) {
        val mDialog = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val ename = mDialogView.findViewById<EditText>(R.id.ename)
        val ecardNumber = mDialogView.findViewById<EditText>(R.id.ecardNumber)
        val eexpirationMonth = mDialogView.findViewById<EditText>(R.id.eexpirationMonth)
        val eexpirationYear = mDialogView.findViewById<EditText>(R.id.eexpirationYear)
        val ecvv = mDialogView.findViewById<EditText>(R.id.ecvv)
        val ebtn = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        ename.setText(card.name)
        ecardNumber.setText(card.cardNumber)
        eexpirationMonth.setText(card.expirationMonth)
        eexpirationYear.setText(card.expirationYear)
        ecvv.setText(card.cvv)

        mDialog.setTitle("Updating Credit Card")

        val alertDialog = mDialog.create()
        alertDialog.show()

        ebtn.setOnClickListener {
            updateCreditCard(
                card.cardId,
                ename.text.toString(),
                ecardNumber.text.toString(),
                eexpirationMonth.text.toString(),
                eexpirationYear.text.toString(),
                ecvv.text.toString()
            )

            Toast.makeText(requireContext(), "Credit Card Updated", Toast.LENGTH_SHORT).show()

            alertDialog.dismiss()
        }
    }

    private fun updateCreditCard(
        cardId: String?,
        name: String,
        cardNumber: String,
        expirationMonth: String,
        expirationYear: String,
        cvv: String
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && cardId != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("CreditCards").child(cardId)

            val updatedCard = AddCardModal(cardId, userId, name, cardNumber, expirationMonth, expirationYear, cvv)

            dbRef.setValue(updatedCard)
        }
    }
}
