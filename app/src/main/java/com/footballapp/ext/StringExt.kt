package com.footballapp.ext

import android.util.Patterns
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString

fun TextView.stringConnector(s1: String?, s2: String?, s3: String?) {
    text = buildSpannedString {
        bold { append("$s1 ") }
        append("$s2")
        if (!s3.isNullOrEmpty()) {
            append(" - $s3")
        }
    }
}

fun String.emailValidation(): String? =
    if (Patterns.EMAIL_ADDRESS.matcher(this).matches()) this else null