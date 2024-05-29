package com.example.unilife.Model

import java.io.Serializable

data class Attivita(
    val titolo:String = "",
    val data:String = "",
    val partecipanti: MutableMap<String,Boolean> = mutableMapOf()):Serializable
