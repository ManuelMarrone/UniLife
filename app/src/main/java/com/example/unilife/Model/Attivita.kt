package com.example.unilife.Model

import java.time.LocalDate

data class Attivita(
    val titolo:String = "",
    val data:LocalDate=LocalDate.of(1970, 1, 1),
    val partecipanti: MutableMap<String,Boolean> = mutableMapOf())
