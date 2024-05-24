package com.example.unilife.Model

data class Gruppo(
    val partecipanti: MutableList<String> = mutableListOf(),
    val listaSpesa: MutableList<String> = mutableListOf(),
    val contatti: MutableMap<String,String> = mutableMapOf(),
    val documenti: MutableMap<String, String> = mutableMapOf(),
)
