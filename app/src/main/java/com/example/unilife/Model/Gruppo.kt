package com.example.unilife.Model

data class Gruppo (
    val nome: String?= null,
    val partecipanti: MutableList<Utente> = mutableListOf(),
)
