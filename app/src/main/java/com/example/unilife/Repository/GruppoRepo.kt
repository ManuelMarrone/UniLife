package com.example.unilife.Repository

import android.util.Log
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Pagamento
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore


//il repository decide come prendere il dato, decide queste polithce, il viewModel ottiene i dati
//senza sapere come sono stati ricavati, disaccoppiare recupero dati dalla loro manipolazione
class GruppoRepo {

    private val db = Firebase.firestore

    fun creaGruppo(username: String): Task<DocumentReference> {
        val partecipanti = mutableListOf<String>(username)
        val listaSpesa = mutableListOf<String>()
        val contatti = mutableMapOf<String,String>()

        val gruppo = Gruppo(partecipanti = partecipanti, listaSpesa, contatti)
        Log.d("crea gruppo inf", "repo")

        return db.collection("gruppi").add(gruppo)
    }


    fun aggiungiPartecipante(username:String, idGruppo: String): Task<Void> {
        val gruppoReference: DocumentReference = db.collection("gruppi").document(idGruppo)

        return gruppoReference.update("partecipanti" , arrayUnion(username))
    }


    fun aggiungiAttivita(attivita: Attivita, idGruppo: String): Task<DocumentReference> {
        val gruppoReference: DocumentReference = db.collection("gruppi").document(idGruppo)

        return gruppoReference.collection("attivita").add(attivita)
    }

    fun aggiungiPagamento(pagamento: Pagamento, idGruppo: String): Task<DocumentReference> {
        val gruppoReference: DocumentReference = db.collection("gruppi").document(idGruppo)

        return gruppoReference.collection("pagamenti").add(pagamento)
    }


    fun modificaAttivita(idAttivita: String , attivita: Attivita, idGruppo: String): Task<Void> {

        val gruppoReference: DocumentReference =  db.collection("gruppi").document(idGruppo)
        val attivitaReference = gruppoReference.collection("attivita").document(idAttivita)


        return attivitaReference.set(attivita, SetOptions.merge())
    }

    fun modificaPagamento(idPagamento: String ,pagamento: Pagamento, idGruppo: String): Task<Void> {

        val gruppoReference: DocumentReference =  db.collection("gruppi").document(idGruppo)
        val pagamentoReference = gruppoReference.collection("pagamenti").document(idPagamento)


        return pagamentoReference.set(pagamento)
    }


    fun aggiungiElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayUnion(nome))
    }

    fun rimuoviElementoListaSpesa(nome: String, idGruppo: String): Task<Void> {
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        return gruppoReference.update("listaSpesa", arrayRemove(nome))
    }

    fun aggiungiContatto(nomeContatto: String, numTel:String, idGruppo: String): Task<Void> {
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        val nuovoContatto: MutableMap<String, String> = mutableMapOf(nomeContatto to numTel)
        return gruppoReference.get().continueWithTask { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {

                    val contatti =
                        document.get("contatti") as? MutableMap<String, String> ?: mutableMapOf()

                    contatti.putAll(nuovoContatto)

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
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        return gruppoReference.update("contatti.$chiave", FieldValue.delete())
    }

    fun eliminaGruppo(idGruppo: String): Task<Void> {
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        return gruppoReference.delete()
    }

    fun rimuoviPartecipante(username: String, idGruppo: String): Task<Void> {
        val gruppoReference = db.collection("gruppi").document(idGruppo)
        return gruppoReference.update("partecipanti", arrayRemove(username))
    }

    fun rimuoviPartecipanteAttivita(idAttivita : String, idGruppo: String, partecipantiAttivita: Map<String, Boolean>): Task<Void> {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)
        val attivitaDoc = gruppoDoc.collection("attivita").document(idAttivita)
        return attivitaDoc.update("partecipanti", partecipantiAttivita)
    }

    fun rimuoviPartecipantePagamento(idPagamento: String, idGruppo: String, partecipantiPagamento: Map<String, Boolean>): Task<Void> {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)
        val pagamentoDoc = gruppoDoc.collection("pagamenti").document(idPagamento)
        return pagamentoDoc.update("partecipanti", partecipantiPagamento)
    }

    fun getGruppo(idGruppo: String): DocumentReference {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)
        return gruppoDoc
    }

    fun getAttivitaByData(data: String, idGruppo: String): Task<QuerySnapshot> {
        val documentReference = db.collection("gruppi").document(idGruppo)

        return documentReference.collection("attivita").whereEqualTo("data", data).get()
    }

    fun fetchPagamenti( idGruppo: String): Task<QuerySnapshot> {
        val documentReference = db.collection("gruppi").document(idGruppo)

        return documentReference.collection("pagamenti").get()
    }

    fun rimuoviAttivita(id:String, idGruppo: String): Task<Void> {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)
        val attivitaDoc = gruppoDoc.collection("attivita").document(id)
        return attivitaDoc.delete()
    }

    fun rimuoviPagamento(id:String, idGruppo: String): Task<Void> {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)
        val pagamentoDoc = gruppoDoc.collection("pagamenti").document(id)
        return pagamentoDoc.delete()
    }


    fun aggiornaPartecipante(idGruppo: String, user: String, username: String) {
        val gruppoDoc = db.collection("gruppi").document(idGruppo)

        gruppoDoc.get().addOnSuccessListener { documentSnapshot ->
            val partecipanti = documentSnapshot?.get("partecipanti") as? MutableList<String>
            Log.d("AggiornaUser", "Partecipanti ${partecipanti}")
            if (partecipanti != null) {
                if (partecipanti.contains(username)) {
                    val indiceUtente = partecipanti.indexOf(username)
                    partecipanti[indiceUtente] = user

                    // Aggiorna il campo "partecipanti" con la nuova lista
                    gruppoDoc.update("partecipanti", partecipanti).addOnSuccessListener {
                        Log.d("AggiornaUser", "Partecipante aggiornato con successo!")
                    }.addOnFailureListener { exception ->
                        Log.e("AggiornaUser", "Errore durante l'aggiornamento del partecipante: $exception")
                    }
                } else {
                    Log.e("AggiornaUser", "L'utente $user non è presente nell'elenco dei partecipanti.")
                }
            } else {
                Log.e("AggiornaUser", "Lista dei partecipanti non presente nel documento.")
            }
        }.addOnFailureListener { exception ->
            Log.e("AggiornaUser", "Errore durante il recupero del documento gruppo: $exception")
        }
    }

    fun eliminaPagamenti(groupId: String){
        val fileRef = db.collection("gruppi").document(groupId).collection("pagamenti")
        fileRef.get()
            .addOnSuccessListener { documents ->
                val batch = db.batch()
                for (document in documents) {
                    batch.delete(document.reference)
                }
                batch.commit()
                    .addOnSuccessListener {
                        Log.d("EliminaCollezione", "Tutti i documenti eliminati con successo.")
                    }
                    .addOnFailureListener { e ->
                        Log.w("EliminaCollezione", "Errore durante l'eliminazione dei documenti", e)
                    }
            }

    }

    fun eliminaAttivita(groupId: String){
        val fileRef = db.collection("gruppi").document(groupId).collection("attivita")
        fileRef.get()
            .addOnSuccessListener { documents ->
                val batch = db.batch()
                for (document in documents) {
                    batch.delete(document.reference)
                }
                batch.commit()
                    .addOnSuccessListener {
                        Log.d("EliminaCollezione", "Tutti i documenti eliminati con successo.")
                    }
                    .addOnFailureListener { e ->
                        Log.w("EliminaCollezione", "Errore durante l'eliminazione dei documenti", e)
                    }
            }

    }

}



