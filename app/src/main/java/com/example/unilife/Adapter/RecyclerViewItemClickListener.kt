package com.example.unilife.Adapter
//interfaccia, serve per notificare i cambiamenti tra adapter e fragment
interface RecyclerViewItemClickListener<T>{
    fun onItemClick(position: T)
}