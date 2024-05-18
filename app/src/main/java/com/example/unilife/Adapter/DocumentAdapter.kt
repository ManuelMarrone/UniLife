package com.example.unilife.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Model.Documento
import com.example.unilife.R

class DocumentAdapter(val listener: RecyclerViewItemClickListener, val listaDocumenti:ArrayList<Documento>): RecyclerView.Adapter<DocumentAdapter.DocumentiViewHolder>() {


    // This is where u inflate the layout(giving a look to out rows)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentiViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return DocumentiViewHolder(layout)
    }


    //assign values to the views we created in the recycler_view_row layout file
    //based on the position of the recycler view
    override fun onBindViewHolder(holder: DocumentiViewHolder, position: Int) {
        holder.textView.text = listaDocumenti.get(position).toString()
    }

    //the number of items u want displayed
    override fun getItemCount(): Int = listaDocumenti.size


    //assign the views of the item to a variable
    inner class DocumentiViewHolder(val riga: View) : RecyclerView.ViewHolder(riga)
    {
        val textView: TextView
        val button : ImageButton

        init{
            textView = riga.findViewById(R.id.documento)
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