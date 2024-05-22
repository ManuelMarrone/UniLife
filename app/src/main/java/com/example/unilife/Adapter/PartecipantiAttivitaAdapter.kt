package com.example.unilife.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class PartecipantiAttivitaAdapter(val c:Context, val listener :RecyclerViewDeleteClickListener<String>, val partecipanti:ArrayList<String>): RecyclerView.Adapter<PartecipantiAttivitaAdapter.PartecipantiViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartecipantiViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_item, parent, false)
        return PartecipantiViewHolder(layout)
    }

    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: PartecipantiViewHolder, position: Int) {
        holder.text.text = partecipanti[position]
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = partecipanti.size


    //assign the views of the item to a variable
    inner class PartecipantiViewHolder(val riga:View) : RecyclerView.ViewHolder(riga)
    {
        val text: CheckBox

        init {
            text = riga.findViewById(R.id.checkBox)
            text.setOnClickListener {
                val posizione = adapterPosition
                if (posizione != RecyclerView.NO_POSITION) {
                    val username = partecipanti[posizione]
                    onItemClick(username)
                }
            }
        }


        private fun onItemClick(username:String)
        {
            listener.onDeleteClick(username)
        }

    }
}