package com.footballapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(private val initialState: LoginData) : ViewModel() {

    private val _isLoginDataValid = MutableLiveData<LoginData>()

    val isLoginDataValid: LiveData<LoginData> = _isLoginDataValid

    fun setupLoginData(login: String, password: String) {
        _isLoginDataValid.value = _isLoginDataValid.value?.copy(login, password) ?: initialState
    }
}
