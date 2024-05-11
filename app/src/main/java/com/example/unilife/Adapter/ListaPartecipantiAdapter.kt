package com.example.unilife.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Model.Utente
import com.example.unilife.R

class ListaPartecipantiAdapter(private val partecipanti:ArrayList<String>): RecyclerView.Adapter<ListaPartecipantiAdapter.PartecipantiViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartecipantiViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_partecipante_gruppo, parent, false)
        return PartecipantiViewHolder(layout)
    }

    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: PartecipantiViewHolder, position: Int) {
        holder.textView.text = partecipanti.get(position)
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = partecipanti.size

    //assign the views of the item to a variable
    class PartecipantiViewHolder(val riga:View) : RecyclerView.ViewHolder(riga)
    {
        val textView: TextView = riga.findViewById<TextView>(R.id.utente)
    }
}