package com.footballapp.ext

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorText(errorText: String?) {
    error = errorText
}