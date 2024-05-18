package com.example.unilife.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class ListaSpesaAdapter( val listener :RecyclerViewItemClickListener<Int>, val listaSpesa:ArrayList<String>): RecyclerView.Adapter<ListaSpesaAdapter.SpesaViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpesaViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_spesa, parent, false)
        return SpesaViewHolder(layout)
    }

    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: SpesaViewHolder, position: Int) {
        holder.textView.text = listaSpesa.get(position)
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = listaSpesa.size


    //assign the views of the item to a variable
    inner class SpesaViewHolder(val riga:View) : RecyclerView.ViewHolder(riga)
    {
        val textView: TextView
        val button : ImageButton

        init{
            textView = riga.findViewById(R.id.nome)
            button = riga.findViewById(R.id.deleteImageButton)
            button.setOnClickListener {
                eliminaItem()}
        }

        private fun eliminaItem()
        {
            Log.d("posizione", " adap${adapterPosition}")
            listener.onItemClick(adapterPosition)
            notifyDataSetChanged()
        }

    }
}