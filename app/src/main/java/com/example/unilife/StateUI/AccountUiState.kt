package com.example.unilife.StateUI

data class AccountUiState(
    var isLoggedOut : Boolean = false
) {
    companion object{
        fun logout() = AccountUiState(isLoggedOut = true)
    }
}