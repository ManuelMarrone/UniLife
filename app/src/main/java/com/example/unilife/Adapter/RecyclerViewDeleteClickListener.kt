package com.example.unilife.Adapter
//interfaccia, serve per notificare i cambiamenti tra adapter e fragment
interface RecyclerViewDeleteClickListener<T>{
    fun onDeleteClick(position: T)
}