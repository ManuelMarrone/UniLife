package com.example.unilife.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class ContattiAdapter(val listener : RecyclerViewButtonClickListener<String>, val contatti:MutableMap<String,String>): RecyclerView.Adapter<ContattiAdapter.ContattiViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContattiViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_contatto, parent, false)
        return ContattiViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ContattiViewHolder, position: Int) {
        val keys = contatti.keys.toList()
        val currentKey = keys[position]
        val currentValue = contatti[currentKey]
        holder.keyTextView.text = currentKey
        holder.valueTextView.text = currentValue
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = contatti.size


    //assign the views of the item to a variable
    inner class ContattiViewHolder(riga:View) : RecyclerView.ViewHolder(riga)
    {
        val keyTextView: TextView
        val valueTextView:TextView
        val button : ImageButton

        init {
            keyTextView = riga.findViewById(R.id.nome)
            valueTextView = riga.findViewById(R.id.numero)
            button = riga.findViewById(R.id.deleteImageButton)
            button.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val chiave = contatti.keys.toList()[position]
                    eliminaItem(chiave)
                }
            }
        }


        private fun eliminaItem(chiave: String) {
            listener.onButtonClick(chiave)
        }



    }
}