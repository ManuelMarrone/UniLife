package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Pagamento
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

class ListaPagamentiViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _listaPagamenti = MutableLiveData<ArrayList<String>>()
    val listaPagamenti: LiveData<ArrayList<String>> get() = _listaPagamenti

    private var idGruppo: String? = null

    private var listaPagamentiObj: MutableMap<String, Pagamento> = mutableMapOf()

    private var _pagamento = MutableLiveData<MutableMap<String, Pagamento>>()
    val pagamento: LiveData<MutableMap<String, Pagamento>> get() = _pagamento

    init {
        getIdGruppoUtente()
    }

    fun loadData() {
        //inizializzazione prendendo i dati dal repo
        if (idGruppo != null) {
            gruppoRepo.getGruppo(idGruppo!!).addSnapshotListener { gruppo, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (gruppo?.toObject(Gruppo::class.java)?.listaSpesa != null) {
                    val pagamenti =
                        gruppoRepo.fetchPagamenti(idGruppo!!).addOnSuccessListener { listaPagamenti ->
                            if (!listaPagamenti.isEmpty) {
                                val lista: ArrayList<String> = ArrayList()
                                for (document in listaPagamenti.documents) {
                                    //conversione a oggetto
                                    val pagamento = document.toObject(Pagamento::class.java)!!
                                    listaPagamentiObj[document.id] = pagamento

                                    val titoloPagamento =
                                        document.toObject(Pagamento::class.java)!!.titolo
                                    Log.d("listaPagamenti", "elementi ${titoloPagamento}")
                                    lista.add(titoloPagamento)
                                }
                                _listaPagamenti.value = lista
                                Log.d("listaPagamenti", "lista ${_listaPagamenti.value}")
                            } else {
                                _listaPagamenti.value = ArrayList()
                                Log.d(
                                    "listaPagamenti",
                                    "Nessun documento trovato."
                                )
                            }
                        }.addOnFailureListener { e ->
                            Log.d("listaPagamenti", "eccezione ${e}")
                        }

                }
            }
        }
    }

    fun getIdGruppoUtente() {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            loadData()
        }
    }
    fun rimuoviPagamento(position:Int)
    {
        if (idGruppo != null) {

            //ricavo l'oggetto dalla posizione per ricavarmi l'id
            val pagamento = listaPagamentiObj.entries.elementAtOrNull(position)
            if (pagamento != null) {
                gruppoRepo.rimuoviPagamento(pagamento.key, idGruppo!!).addOnFailureListener()
                {e->
                    Log.d("RimozionePagamento", "failed remove ${e}")
                }
                val updatedList = _listaPagamenti.value
                updatedList?.removeAt(position)
                _listaPagamenti.value = updatedList!!
            }

        }
    }

    fun visualizzaItem(posizione:Int)
    {
        if (idGruppo != null) {
            val pagamento: Pagamento? = listaPagamentiObj.entries.elementAtOrNull(posizione)?.value
            if (pagamento != null) {
                val key =  listaPagamentiObj.entries.elementAtOrNull(posizione)?.key
                if (key != null) {
                    val map : MutableMap<String, Pagamento> = mutableMapOf(key to pagamento)
                    _pagamento.value = map
                    Log.d("VisualizzaPagamento", "Pagamento nel VM ${_pagamento.value}")
                }
            }

        }
    }
}