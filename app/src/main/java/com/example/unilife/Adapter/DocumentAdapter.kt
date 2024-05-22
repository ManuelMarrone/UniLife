package com.example.unilife.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class DocumentAdapter(val listener: RecyclerViewDeleteClickListener<Int>, val listaDocumenti:MutableMap<String,String>): RecyclerView.Adapter<DocumentAdapter.DocumentiViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentiViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return DocumentiViewHolder(layout)
    }


    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: DocumentiViewHolder, position: Int) {
        val keys = listaDocumenti.keys.toList()
        val currentKey = keys[position]
        val currentValue = listaDocumenti[currentKey]
        holder.keyTextView.text = currentKey
        holder.valueTextView.text = currentValue
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = listaDocumenti.size


    //assign the views of the item to a variable
    inner class DocumentiViewHolder(val riga: View) : RecyclerView.ViewHolder(riga)
    {
        val keyTextView: TextView
        val valueTextView:TextView
        val button : ImageButton

        init{
            keyTextView = riga.findViewById(R.id.documento)
            valueTextView = riga.findViewById(R.id.numero)
            button = riga.findViewById(R.id.deleteImageButton)
            button.setOnClickListener {
                eliminaItem()}
        }

        private fun eliminaItem()
        {
            Log.d("posizione", " adap${adapterPosition}")
            listener.onDeleteClick(adapterPosition)
            notifyDataSetChanged()
        }

    }


}