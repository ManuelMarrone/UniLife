package com.example.unilife.Repository

import android.util.Log
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Gruppo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions


//il repository decide come prendere il dato, decide queste polithce, il viewModel ottiene i dati
//senza sapere come sono stati ricavati, disaccoppiare recupero dati dalla loro manipolazione
class GruppoRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val utenteRepo = UtenteRepo()
    fun creaGruppo(username: String): Task<DocumentReference> {
        val partecipanti = mutableListOf<String>(username)
        val listaSpesa = mutableListOf<String>()
        val contatti = mutableMapOf<String,String>()
        val gruppo = Gruppo(partecipanti = partecipanti, listaSpesa, contatti)
        Log.d("crea gruppo inf", "repo")

        return dbSettings.firestore.collection("gruppi").add(gruppo)
    }


    fun aggiungiPartecipante(username:String, idGruppo: String): Task<Void> {
        val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        return gruppoReference.update("partecipanti" , arrayUnion(username))
    }

    //aggiunge il documento dentro la raccolta attivita dentro la raccolta gruppi
    fun aggiungiAttivita(attivita: Attivita, idGruppo: String): Task<DocumentReference> {
        val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        return gruppoReference.collection("attivita").add(attivita)
    }


    fun aggiungiElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayUnion(nome))
    }

    fun rimuoviElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayRemove(nome))
    }

    fun aggiungiContatto(nomeContatto: String, numTel:String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        val nuovoContatto: MutableMap<String, String> = mutableMapOf(nomeContatto to numTel)
        return gruppoReference.get().continueWithTask { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {
                    // Recuperare la mappa dei contatti esistente dal documento
                    val contatti =
                        document.get("contatti") as? MutableMap<String, String> ?: mutableMapOf()

                    // Aggiungere il nuovo contatto alla mappa dei contatti esistente
                    contatti.putAll(nuovoContatto)

                    // Aggiornare il documento con la nuova mappa dei contatti
                    return@continueWithTask gruppoReference.set(
                        mapOf("contatti" to contatti),
                        SetOptions.merge()
                    )
                } else {
                    throw Exception("Il documento non esiste")
                }
            } else {
                throw task.exception ?: Exception("Errore nel recupero del documento")
            }
        }
    }

    fun rimuoviContatto(chiave: String, idGruppo: String): Task<Void> {
        val gruppoReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
        return gruppoReference.update("contatti.$chiave", FieldValue.delete())
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

    fun getAttivitaByData(data: String, idGruppo: String): Task<QuerySnapshot> {
        val documentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        return documentReference.collection("attivita").whereEqualTo("data", data).get()
    }

}



