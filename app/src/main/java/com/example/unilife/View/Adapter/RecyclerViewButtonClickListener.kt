package com.example.unilife.View.Adapter
//interfaccia, serve per notificare i cambiamenti tra adapter e fragment
interface RecyclerViewButtonClickListener<T>{
    fun onButtonClick(position: T)
}