package com.footballapp.ext

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.showErrorMassage(isError: Boolean, errorText: String){
    error = if (isError) errorText else null
}