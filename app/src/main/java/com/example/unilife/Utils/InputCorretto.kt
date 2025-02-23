package com.example.unilife.Utils

class InputCorretto {

    /**
     * Verifica se l'username contiene solo lettere dell'alfabeto, numeri, caratteri speciali e non è vuoto
     */
    fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9\\p{Punct}]+$")
        return username.isNotBlank() && !username.contains(" ") && regex.matches(username)
    }

    /**
     * Verifica se l'email è valida (non vuota, non contiene spazi e rispetta il formato di un'email)
     */
    fun isValidEmail(email: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return email.isNotBlank() && !email.contains(" ") && regex.matches(email)
    }

    /**
     * Verifica se la password è valida (non vuota, non contiene spazi e lunga almeno 6 caratteri)
     */
    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && !password.contains(" ") && password.length >= 6
    }

    /**
     * Verifica se il numero di telefono è formato da 10 cifre
     */
    fun isValidPhone(telefono: String): Boolean {
        return telefono.isNotEmpty() && telefono.length == 10
    }


}