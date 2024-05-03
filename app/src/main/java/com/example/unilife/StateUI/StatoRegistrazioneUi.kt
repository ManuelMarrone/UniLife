package com.example.unilife.StateUI

data class StatoRegistrazioneUi(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
) {
    companion object {
        fun loading() = StatoRegistrazioneUi(isLoading = true)
        fun success() = StatoRegistrazioneUi(isLoggedIn = true)
        fun error(toString: String) = StatoRegistrazioneUi(error = toString)
    }
}

