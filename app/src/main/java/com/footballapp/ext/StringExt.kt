package com.footballapp.ext

import android.util.Patterns

fun stringConnector(s1: String?, s2: String?)  = "$s1 - $s2"

fun String.emailValidation(): String? =
    if (Patterns.EMAIL_ADDRESS.matcher(this).matches()) this else null