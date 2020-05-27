package com.footballapp.ui.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.core.KoinComponent
import org.koin.core.inject

class FirebaseUserLiveData : LiveData<FirebaseUser?>(), KoinComponent {

    private val firebaseAuth by inject<FirebaseAuth>()

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }
}