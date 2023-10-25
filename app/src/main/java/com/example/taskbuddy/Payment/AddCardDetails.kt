package com.example.taskbuddy.Payment

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.taskbuddy.Modals.AddCardModal
import com.example.taskbuddy.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddCardDetails : BottomSheetDialogFragment() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var name: EditText
    private lateinit var cardNumber: EditText
    private lateinit var expirationMonth: EditText
    private lateinit var expirationYear: EditText
    private lateinit var cvv: EditText
    private lateinit var submitBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_add_credit_card, container, false)

        // Initialize Firebase Database reference
        dbRef = FirebaseDatabase.getInstance().reference.child("CreditCards")
        auth = Firebase.auth

        name = view.findViewById(R.id.name)
        cardNumber = view.findViewById(R.id.card_number)
        expirationMonth = view.findViewById(R.id.month)
        expirationYear = view.findViewById(R.id.year)
        cvv = view.findViewById(R.id.cvv)
        submitBtn = view.findViewById(R.id.submit_button)

        cardNumber.filters = arrayOf(InputFilter.LengthFilter(19))
        cvv.filters = arrayOf(InputFilter.LengthFilter(3))
        expirationMonth.filters = arrayOf(InputFilter.LengthFilter(2))
        expirationYear.filters = arrayOf(InputFilter.LengthFilter(2))

        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val originalText = editable.toString().replace(" ", "")
                val formattedText = StringBuilder()
                for (i in originalText.indices) {
                    formattedText.append(originalText[i])
                    if ((i + 1) % 4 == 0 && i != originalText.length - 1) {
                        formattedText.append(" ")
                    }
                }
                cardNumber.removeTextChangedListener(this)
                cardNumber.setText(formattedText)
                cardNumber.setSelection(formattedText.length)
                cardNumber.addTextChangedListener(this)
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        submitBtn.setBackgroundResource(R.drawable.round_button_background)

        submitBtn.setOnClickListener {
            saveCreditCard()
        }

        return view
    }

    private fun saveCreditCard() {
        val nameText = name.text.toString()
        val cardNumberText = cardNumber.text.toString()
        val expirationMonthText = expirationMonth.text.toString()
        val expirationYearText = expirationYear.text.toString()
        val cvvText = cvv.text.toString()

        // Validate credit card details
        if (nameText.isEmpty() ||
            cardNumberText.isEmpty() ||
            expirationMonthText.isEmpty() || !isValidExpirationDate(expirationMonthText) ||
            expirationYearText.isEmpty() || !isValidExpirationDate(expirationYearText) ||
            cvvText.isEmpty() || cvvText.length != 3
        ) {
            // Display an error toast if validation fails
            Toast.makeText(requireContext(), "Invalid credit card details", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the user is authenticated
        val user = auth.currentUser
        if (user == null) {
            // Display an error toast if the user is not authenticated
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the user's UID
        val uid = user.uid

        // Create a credit card object
        val creditCard = AddCardModal(
            cardId = null,
            userId = uid,
            name = nameText,
            cardNumber = cardNumberText,
            expirationMonth = expirationMonthText,
            expirationYear = expirationYearText,
            cvv = cvvText
        )

        // Save credit card data to the Firebase Realtime Database
        val cardId = dbRef.push().key
        if (cardId != null) {
            creditCard.cardId = cardId // Set the cardId
            dbRef.child(cardId).setValue(creditCard)
                .addOnCompleteListener {
                    // Display a success toast
                    Toast.makeText(requireContext(), "Credit card added successfully", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    // Display an error toast in case of failure
                    Toast.makeText(requireContext(), "Failed to add credit card: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun isValidExpirationDate(expirationDate: String): Boolean {
        // Implement your own validation logic for expiration dates (e.g., MM/YY)
        // Return true if the date is valid, false otherwise
        // Add your validation logic here
        return true
    }

    private fun clearFields() {
        name.text.clear()
        cardNumber.text.clear()
        expirationYear.text.clear()
        expirationMonth.text.clear()
        cvv.text.clear()
    }
}
