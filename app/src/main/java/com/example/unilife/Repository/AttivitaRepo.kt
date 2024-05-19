package com.example.unilife.Repository

import com.google.firebase.auth.FirebaseAuth

class AttivitaRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val utenteRepo = GruppoRepo()
}