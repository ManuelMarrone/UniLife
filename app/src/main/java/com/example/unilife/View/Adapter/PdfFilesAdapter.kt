package com.example.unilife.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Model.Documento
import com.example.unilife.databinding.EachPdfItemBinding


class PdfFilesAdapter : ListAdapter<Documento, PdfFilesAdapter.PdfFilesViewHolder>(PdfDiffCallback()){

    inner class PdfFilesViewHolder(private val binding: EachPdfItemBinding) : RecyclerView.ViewHolder(binding.root){

      init{
          binding.root.setOnClickListener{

          }
      }

        fun bind(data : Documento) {
            binding.fileName.text = data.nome_doc

        }
        }
    class PdfDiffCallback: DiffUtil.ItemCallback<Documento>(){
        override fun areItemsTheSame(oldItem: Documento, newItem: Documento) =
            oldItem.url == newItem.url



        override fun areContentsTheSame(oldItem: Documento, newItem: Documento) = oldItem == newItem


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfFilesViewHolder {
        val binding = EachPdfItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PdfFilesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PdfFilesViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

}