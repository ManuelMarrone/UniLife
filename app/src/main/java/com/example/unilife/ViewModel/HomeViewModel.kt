package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    fun rimuoviElemento(nome: String)
    {
        viewModelScope.launch {
            gruppoRepo.rimuoviElemento(nome)
        }
    }
    fun getListaSpesaLive(): LiveData<ArrayList<String>> {
        return gruppoRepo.getListaSpesa()
    }
    fun aggiungiElemento(nome:String)
    {
        gruppoRepo.aggiungiElemento(nome)
    }

    fun getListaSpesa(callback: (ArrayList<String>?) -> Unit){
        //chiamata al repository per prendere l'informazione
        gruppoRepo.getListaSpesa(callback)
    }
}