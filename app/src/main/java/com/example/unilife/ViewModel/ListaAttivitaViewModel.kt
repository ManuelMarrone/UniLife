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

    init {
        _listaAttivita.value = ArrayList()
    }


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
                                // Ottieni i dati di ogni documento
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

//    fun rimuoviAttivita(position:Int)
//    {
//        utenteRepo.getUtente().addOnSuccessListener { utente ->
//            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
//            if (idGruppo != null) {
//                gruppoRepo.rimuoviAttivita(_listaAttivita.value?.get(position))
//            }
//            }
//        }
//    }




}
