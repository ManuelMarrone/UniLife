package com.example.unilife.Model

data class Gruppo (
    val partecipanti: MutableList<String> = mutableListOf(),
    val listaSpesa: MutableList<String> = mutableListOf(),
)
