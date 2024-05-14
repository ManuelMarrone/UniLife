package com.example.unilife.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R

class ListaPartecipantiAdapter(val c:Context, val listener :RecyclerViewItemClickListener, val partecipanti:ArrayList<String>): RecyclerView.Adapter<ListaPartecipantiAdapter.PartecipantiViewHolder>() {


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
    inner class PartecipantiViewHolder(val riga:View) : RecyclerView.ViewHolder(riga)
    {
        val textView: TextView
        val button : ImageButton

        init{
            textView = riga.findViewById(R.id.spesa)
            button = riga.findViewById(R.id.deleteImageButton)
            button.setOnClickListener {
                eliminaItem(it)}
        }

        private fun eliminaItem(v:View)
        {
            AlertDialog.Builder(c)
                .setTitle("Elimina")
                .setMessage("Sicuro di voler eliminare questo partecipante?")
                .setPositiveButton("Sì"){
                    dialog,_->
                    listener.onItemClick(adapterPosition)
                    partecipanti.removeAt(adapterPosition)
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                    dialog,_->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    }
}