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

class ModificaAggiungiSpesaViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _partecipanti = MutableLiveData<MutableMap<String,Boolean>>()
    val partecipanti: LiveData<MutableMap<String, Boolean>> get() = _partecipanti

    private var _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private var idGruppo : String? = null


    private var pagamentoId : String = ""

    init {
        getIdGruppoUtente()
    }

    fun loadData() {

            // Carica i partecipanti del Gruppo
            if (idGruppo != null) {
                gruppoRepo.getGruppo(idGruppo!!).addSnapshotListener { gruppo, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    val partecipantiGruppo = gruppo?.toObject(Gruppo::class.java)?.partecipanti as ArrayList<String>
                    val newMap = partecipantiGruppo.associateWith { false } as MutableMap<String, Boolean>
                    _partecipanti.value = newMap
                }
            }
    }

    fun getIdGruppoUtente()
    {
        utenteRepo.getUtente()?.addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            loadData()
        }
    }

    fun setChecked(username: String)
    {
        //cambia lo stato da false a true o viceversa dell'utente cliccato nella recyclerView
        val valore = !(_partecipanti.value?.get(username))!!
        _partecipanti.value?.set(username, valore)
    }

    fun validaInput()
    {
        _isValid.value = _partecipanti.value!!.values.any { it == true }
    }

    fun aggiungiPagamento(titolo: String, denaro: Double) {
        if (idGruppo != null) {
            val partecipantiAttivi = _partecipanti.value!!.filter { it.value }.keys
            val quota = denaro/(partecipantiAttivi.size)
            val pagamento = Pagamento(titolo, denaro,quota, _partecipanti.value!!)
            gruppoRepo.aggiungiPagamento(pagamento, idGruppo!!).addOnFailureListener {e->
                Log.e("pagamento","Failed adding element ${e}")
            }

        }
    }

    fun salvaModifica(idPagamento: String, titolo: String, denaro: Double) {
        if (idGruppo != null) {
            val partecipantiAttivi = _partecipanti.value!!.filter { it.value }.keys
            val quota = denaro/(partecipantiAttivi.size)
            val pagamento = Pagamento(titolo, denaro, quota, _partecipanti.value!!)
            Log.d("pagamentoTest", "part ${_partecipanti.value!!}")
            gruppoRepo.modificaPagamento(idPagamento,pagamento, idGruppo!!).addOnFailureListener { e ->
                Log.e("pagamento", "Failed adding element ${e}")
            }
        }

    }
}