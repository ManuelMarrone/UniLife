package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.getField
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GruppoRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    private val _listaSpesa: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val listaSpesa: LiveData<ArrayList<String>> = _listaSpesa

    private val utenteRepo = UtenteRepo()
    suspend fun creaGruppo(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val idUtente = firebaseAuth.currentUser?.uid!!
            val partecipanti = mutableListOf<String>(idUtente)
            val listaSpesa = mutableListOf<String>()
            val gruppo = Gruppo(partecipanti = partecipanti, listaSpesa)

            val documentReference = dbSettings.firestore.collection("gruppi").add(gruppo).await()
            val nuovoGruppoId = documentReference.id
            utenteRepo.setIdGruppo(nuovoGruppoId)
            Log.d(ContentValues.TAG, "ID del nuovo gruppo: $nuovoGruppoId")

            Result.success(true)
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "Errore durante la creazione del gruppo", e)
            Result.failure(e)
        }
    }


    fun aggiungiPartecipante(idGruppo:String)
    {
        val idUtente = firebaseAuth.currentUser?.uid!!
        val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        gruppoReference.get()
            .addOnSuccessListener { documentSnapshot ->
                dbSettings.firestore.collection("gruppi").document(idGruppo).update("partecipanti" , arrayUnion(idUtente))
            }
            .addOnFailureListener { exception ->
                // Gestisci eventuali errori qui
                Log.w(ContentValues.TAG, "Errore durante la creazione del gruppo", exception)
            }

    }

    fun aggiungiElemento(nome:String)
    {
        utenteRepo.getIdGruppo { idGruppo ->
            val gruppoReference: DocumentReference =
                dbSettings.firestore.collection("gruppi").document(idGruppo!!)

            gruppoReference.get()
                .addOnSuccessListener { documentSnapshot ->
                    gruppoReference.update("listaSpesa", arrayUnion(nome))
                        .addOnSuccessListener {
                            // Aggiorna il LiveData dopo l'aggiunta dell'elemento
                            _listaSpesa.value = _listaSpesa.value?.apply { add(nome) }
                        }
                        .addOnFailureListener { exception ->
                            // Gestisci eventuali errori qui
                            Log.w("aggiungi elemento", "Errore durante l'aggiunta", exception)
                        }
                }
                .addOnFailureListener { exception ->
                    // Gestisci eventuali errori qui
                    Log.w("elimina elemento", "Errore durante l'eliminazione", exception)
                }

        }
    }

    fun getListaSpesa(): LiveData<ArrayList<String>> {
        return listaSpesa
    }

    fun rimuoviPartecipante(username:String)
    {
        utenteRepo.getIdGruppo { idGruppo->
            Log.d("eliminaPartecipante", "id gruppo $idGruppo")
            val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo!!) //sono sicuro che idGruppo non sia nullo
            utenteRepo.getIdUtenteDaUsername(username)
            {
                idUtente->
                Log.d("eliminaPartecipante", "id utente da eliminare $idUtente")
                gruppoReference.update("partecipanti" , arrayRemove(idUtente))
                Log.d("eliminaPartecipante", "gruppo $gruppoReference")
                // Controlla se la lista dei partecipanti è vuota
                gruppoReference.get()
                    .addOnSuccessListener { documentSnapshot ->
                        val partecipanti = documentSnapshot.toObject(Gruppo::class.java)?.partecipanti
                        if (partecipanti == null || partecipanti.isEmpty()) {
                            // Se la lista dei partecipanti è vuota, elimina il documento del gruppo
                            gruppoReference.delete()
                                .addOnSuccessListener {
                                    Log.d("eliminaPartecipante", "Documento del gruppo $idGruppo eliminato")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("eliminaPartecipante", "Errore durante l'eliminazione del documento del gruppo $idGruppo", e)
                                }
                        }
                    }
            }
        }

    }

    suspend fun controllaCodiceInvito(idGruppo: String, callback: (Boolean?) -> Unit) {
        try {
            val documentReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
            val documentSnapshot = withContext(Dispatchers.IO) {
                documentReference.get().await()
            }
            if (documentSnapshot.exists()) {
                callback(true)
            } else {
                callback(false)
            }
        } catch (e: Exception) {
            callback(false)
        }
    }

    fun getListaSpesa(callback: (ArrayList<String>?) -> Unit) {
        utenteRepo.getIdGruppo { idGruppo ->
            var listaSpesa = ArrayList<String>()
            try {
                if (idGruppo != null) {
                    val gruppoReference: DocumentReference =
                        dbSettings.firestore.collection("gruppi").document(idGruppo)

                    gruppoReference.get().addOnSuccessListener { documentSnapshot ->
                        listaSpesa = documentSnapshot.get("listaSpesa") as ArrayList<String>
                        Log.e("listaSPesa", "spesa ${listaSpesa}")
                        callback(listaSpesa)
                    }
                }
            } catch (e: Exception) {
                Log.e("listaSPesa", "Errore durante il recupero degli elementi", e)
                callback(null)
            }
        }
    }

    fun rimuoviElemento(nome:String)
    {

        utenteRepo.getIdGruppo { idGruppo->
            Log.d("eliminaElemento", "id gruppo $idGruppo")
            val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo!!) //sono sicuro che idGruppo non sia nullo
            gruppoReference.update("listaSpesa" , arrayRemove(nome))
        }
    }


}

