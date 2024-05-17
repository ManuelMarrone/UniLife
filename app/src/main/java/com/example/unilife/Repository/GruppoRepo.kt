package com.example.unilife.Repository

import android.util.Log
import com.example.unilife.Model.Gruppo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion


//il repository decide come prendere il dato, decide queste polithce, il viewModel ottiene i dati
//senza sapere come sono stati ricavati, disaccoppiare recupero dati dalla loro manipolazione
class GruppoRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val utenteRepo = UtenteRepo()
    fun creaGruppo(username: String): Task<DocumentReference> {
        val partecipanti = mutableListOf<String>(username)
        val listaSpesa = mutableListOf<String>()
        val gruppo = Gruppo(partecipanti = partecipanti, listaSpesa)
        Log.d("crea gruppo inf", "repo")

        return dbSettings.firestore.collection("gruppi").add(gruppo)
    }


    fun aggiungiPartecipante(username:String, idGruppo: String): Task<Void> {
        val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        return gruppoReference.update("partecipanti" , arrayUnion(username))
    }


    fun aggiungiElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayUnion(nome))
    }

    fun rimuoviElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayRemove(nome))
    }
    fun eliminaGruppo(idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.delete()
    }

    fun rimuoviPartecipante(username: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("partecipanti", arrayRemove(username))
    }

    fun getGruppo(idGruppo: String): DocumentReference {
        val gruppoDoc = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoDoc
    }
}



