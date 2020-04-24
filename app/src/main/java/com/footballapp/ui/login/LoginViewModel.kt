package com.footballapp.ui.login

import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.footballapp.repository.Repository

class LoginViewModel(
    private val initialState: LoginData,
    private val repository: Repository
) : ViewModel() {

    private val _isLoginDataValid = MutableLiveData<LoginData>()

    val isLoginDataValid: LiveData<LoginData> = _isLoginDataValid

    fun setupLoginData(login: String, password: String) {
        _isLoginDataValid.value = _isLoginDataValid.value?.copy(login, password) ?: initialState
    }

    fun hasNetworkConnection(connectivityManager: ConnectivityManager) =
        repository.hasNetworkConnection(connectivityManager)

}
