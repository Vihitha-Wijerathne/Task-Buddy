package com.example.taskbuddy.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.Modals.AddCardModal
import com.example.taskbuddy.R

class CreditCardAdapter(
    private val creditCards: List<AddCardModal>,
    private val onDeleteClick: (AddCardModal) -> Unit,
    private val onUpdateClick: (AddCardModal) -> Unit, // Added onUpdateClick
    private val onSelectCard: (AddCardModal) -> Unit
) : RecyclerView.Adapter<CreditCardAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val expirationMonthTextView: TextView = itemView.findViewById(R.id.expirationMonthTextView)
        val expirationYearTextView: TextView = itemView.findViewById(R.id.expirationYearTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val updateButton: Button = itemView.findViewById(R.id.updateButton) // Added "Update" button

        fun bind(creditCard: AddCardModal, onDeleteClick: (AddCardModal) -> Unit, onUpdateClick: (AddCardModal) -> Unit, onSelectCard: (AddCardModal) -> Unit) {
            cardNumberTextView.text = creditCard.cardNumber
            nameTextView.text = creditCard.name
            expirationMonthTextView.text = "Expiry Date: ${creditCard.expirationMonth}"
            expirationYearTextView.text = "/${creditCard.expirationYear}"

            // Handle delete button click
            deleteButton.setOnClickListener {
                onDeleteClick(creditCard)
            }

            // Handle "Update" button click
            updateButton.setOnClickListener {
                onUpdateClick(creditCard)
            }

            // Handle item click
            itemView.setOnClickListener {
                onSelectCard(creditCard)
            }

            // Set the item as activated if it's the selected card
            itemView.isActivated = adapterPosition == selectedPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_credit_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creditCard = creditCards[position]
        holder.bind(creditCard, onDeleteClick, onUpdateClick, onSelectCard)

        // Set the background based on the isSelected property
        val context = holder.itemView.context
        val backgroundResId = if (creditCard.isSelected) {
            R.drawable.card_selected_background
        } else {
            R.drawable.card_item_background
        }
        holder.itemView.setBackgroundResource(backgroundResId)
    }

    override fun getItemCount(): Int {
        return creditCards.size
    }
}
