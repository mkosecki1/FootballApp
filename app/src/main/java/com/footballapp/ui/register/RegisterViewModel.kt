package com.footballapp.ui.register

import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.footballapp.ext.emailValidation
import com.footballapp.ext.passwordValidation
import com.footballapp.repository.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private val _isRegisterLoginFormatInvalid = MutableLiveData<Boolean>()
    private val _isRegisterPasswordFormatInvalid = MutableLiveData<Boolean>()

    val isRegisterLoginFormatInvalid: LiveData<Boolean> = _isRegisterLoginFormatInvalid
    val isRegisterPasswordFormatInvalid: LiveData<Boolean> = _isRegisterPasswordFormatInvalid

    fun setupRegisterLoginData(login: String, password: String) {
        _isRegisterLoginFormatInvalid.value = login.emailValidation().isNullOrEmpty()
        _isRegisterPasswordFormatInvalid.value = password.passwordValidation().isNullOrEmpty()
    }

    fun hasNetworkConnection(connectivityManager: ConnectivityManager) =
        repository.hasNetworkConnection(connectivityManager)
}
