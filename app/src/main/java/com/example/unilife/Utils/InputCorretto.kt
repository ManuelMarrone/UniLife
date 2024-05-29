package com.example.unilife.Utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class InputCorretto {
//    fun isValidFullName(fullName: String): Boolean {
//        val regex = Regex("^[a-zA-Z ]+\$")
//        return fullName.isNotBlank() && regex.matches(fullName)
//    }

    /**
     * Verifica se l'username contiene solo lettere dell'alfabeto, numeri, caratteri speciali e non è vuoto
     * @param username username dell'utente
     * @return true se l'username è valido, false altrimenti
     */
    fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9\\p{Punct}]+$")
        return username.isNotBlank() && !username.contains(" ") && regex.matches(username)
    }

    /**
     * Verifica se l'email è valida (non vuota, non contiene spazi e rispetta il formato di un'email)
     * @param email email dell'utente
     * @return true se l'email è valida, false altrimenti
     */
    fun isValidEmail(email: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return email.isNotBlank() && !email.contains(" ") && regex.matches(email)
    }

    /**
     * Verifica se la password è valida (non vuota, non contiene spazi e lunga almeno 6 caratteri)
     * @param password password dell'utente
     * @return true se la password è valida, false altrimenti
     */
    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && !password.contains(" ") && password.length >= 6
    }

    fun isValidPhone(telefono: String): Boolean {
        return telefono.isNotEmpty() && telefono.length == 10
    }


}