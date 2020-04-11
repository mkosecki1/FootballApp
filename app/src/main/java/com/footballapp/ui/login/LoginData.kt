package com.footballapp.ui.login

import com.footballapp.ext.emailValidation

data class LoginData(val login: String?, val password: String?) {

    fun checkLoginValidation(errorText: String) : String? =
        when {
            login?.emailValidation().isNullOrEmpty() -> errorText
            else -> null
        }

    fun checkPasswordValidation(errorText: String) : String? =
        when {
            password.isNullOrEmpty() -> errorText
            else -> null
        }
}