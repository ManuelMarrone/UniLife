package com.example.unilife.Model

import java.io.Serializable

class Pagamento (val titolo:String = "",
                 val denaro:Double = 0.0,
                 val quota: Double = 0.0,
                 val partecipanti: MutableMap<String,Boolean> = mutableMapOf()):Serializable