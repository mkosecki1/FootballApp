package com.footballapp.ui.login

import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.footballapp.ext.emailValidation
import com.footballapp.repository.Repository
import com.footballapp.ui.login.FirebaseUserLiveData.AuthenticationState.AUTHENTICATED
import com.footballapp.ui.login.FirebaseUserLiveData.AuthenticationState.UNAUTHENTICATED

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _isLoginFormatInvalid = MutableLiveData<Boolean>()
    private val _isPasswordEmpty = MutableLiveData<Boolean>()

    val isLoginFormatInvalid: LiveData<Boolean> = _isLoginFormatInvalid
    val isPasswordEmpty: LiveData<Boolean> = _isPasswordEmpty

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) AUTHENTICATED else UNAUTHENTICATED
    }

    fun setupLoginData(login: String, password: String) {
        _isLoginFormatInvalid.value = login.emailValidation().isNullOrEmpty()
        _isPasswordEmpty.value = password.isEmpty()
    }

    fun hasNetworkConnection(connectivityManager: ConnectivityManager) =
        repository.hasNetworkConnection(connectivityManager)
}