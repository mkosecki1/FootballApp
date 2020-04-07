package com.footballapp.net

sealed class ResponseCall<out T: Any> {

    data class Success<out T : Any>(val data: T) : ResponseCall<T>()

    data class Error(val code: Int, val message: String) : ResponseCall<Nothing>() {
        override fun toString() = "$code: $message"
    }

    data class Exception(val exception: kotlin.Exception) : ResponseCall<Nothing>()
}