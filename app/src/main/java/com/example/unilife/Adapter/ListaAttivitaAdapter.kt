package com.example.unilife.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class ListaAttivitaAdapter(val c: Context, val listener :RecyclerViewDeleteClickListener<Int>, val itemClicklistener : RecyclerViewItemClickListener, val listaAttivita:ArrayList<String>): RecyclerView.Adapter<ListaAttivitaAdapter.AttivitaViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttivitaViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_attivita, parent, false)
        return AttivitaViewHolder(layout)
    }

    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: AttivitaViewHolder, position: Int) {
        holder.textView.text = listaAttivita.get(position)
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = listaAttivita.size


    //assign the views of the item to a variable
    inner class AttivitaViewHolder(val riga:View) : RecyclerView.ViewHolder(riga)
    {
        val textView: TextView
        val button : ImageButton

        init{
            textView = riga.findViewById(R.id.titolo)
            button = riga.findViewById(R.id.deleteImageButton)
            button.setOnClickListener {
                eliminaItem()}

            riga.setOnClickListener {
                itemClicklistener.onItemClickListener(adapterPosition)
            }
        }

        private fun eliminaItem()
        {
            Log.d("posizione", " adap${adapterPosition}")
            listener.onDeleteClick(adapterPosition)
        }



    }
}
