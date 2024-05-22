package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo
import org.checkerframework.checker.units.qual.A

class ListaAttivitaViewModel:ViewModel() {

    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _listaAttivita = MutableLiveData<ArrayList<String>>()
    val listaAttivita: LiveData<ArrayList<String>> get() = _listaAttivita

    private var idGruppo: String? = null

    private var listaAttivitaObj : MutableMap<String, Attivita> = mutableMapOf()
    init {
        _listaAttivita.value = ArrayList()
    }

//ricava l'array di Attivita e l'array dei titoli delle attività
    fun getAttivitaByData(data:String)
    {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo

            Log.d("listaAttivita", "getAttivita idGruppo ${idGruppo}")
            if (idGruppo != null) {
                gruppoRepo.getAttivitaByData(data, idGruppo!!)
                    .addOnSuccessListener { listaAttivita ->
                        if (!listaAttivita.isEmpty) {
                            val lista : ArrayList<String> = ArrayList()
                            for (document in listaAttivita.documents) {
                                //conversione a oggetto
                                val attivita = document.toObject(Attivita::class.java)!!
                                listaAttivitaObj[document.id] = attivita

                                val titoloAttivita =
                                    document.toObject(Attivita::class.java)!!.titolo
                                Log.d("listaAttivita", "elementi ${titoloAttivita}")
                                lista.add(titoloAttivita)
                            }
                            _listaAttivita.value = lista
                            Log.d("listaAttivita", "lista ${_listaAttivita.value}")
                        } else {
                            _listaAttivita.value = ArrayList()
                            Log.d(
                                "listaAttivita",
                                "Nessun documento trovato con la data specificata."
                            )
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d("listaAttivita", "eccezione ${e}")
                    }
            }
        }
    }


    fun rimuoviAttivita(position:Int)
    {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            if (idGruppo != null) {

                //ricavo l'oggetto attività dalla posizione per ricavarmi l'id
                val attivita = listaAttivitaObj.entries.elementAtOrNull(position)
                if (attivita != null) {
                    gruppoRepo.rimuoviAttivita(attivita.key, idGruppo!!).addOnFailureListener()
                    {e->
                        Log.d("RimozioneAttivita", "failed remove ${e}")
                    }
                    val updatedList = _listaAttivita.value
                    updatedList?.removeAt(position)
                    _listaAttivita.value = updatedList!!
                }

            }
            }
        }
    }


