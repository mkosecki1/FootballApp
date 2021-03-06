package com.footballapp.ui.login

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footballapp.R
import com.footballapp.ext.hideKeyboard
import com.footballapp.ext.runDestination
import com.footballapp.ext.showErrorMassage
import com.footballapp.ext.showSnackBar
import com.footballapp.ui.login.FirebaseUserLiveData.AuthenticationState.AUTHENTICATED
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private val firebaseAuth by inject<FirebaseAuth>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        observeAuthenticationState()

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginDataChange()

        buttonLoginFragment.setOnClickListener {
            loginViewModel.setupLoginData(
                textEditUserNameLoginFragment.text.toString().trim(),
                textEditPasswordLoginFragment.text.toString().trim()
            )
            checkInternetConnection()
            view.hideKeyboard()
        }

        buttonRegisterLoginFragment.setOnClickListener {
            runDestination(this, R.id.registrationFragment)
        }
    }

    private fun observeLoginDataChange() {
        with(loginViewModel) {
            isLoginFormatInvalid.observe(
                viewLifecycleOwner,
                Observer { login ->
                    textInputUserNameLoginFragment.showErrorMassage(
                        login,
                        getString(R.string.login_fragment_error_invalid_format_text)
                    )
                })

            isPasswordEmpty.observe(
                viewLifecycleOwner, Observer { password ->
                    textInputPasswordLoginFragment.showErrorMassage(
                        password,
                        getString(R.string.login_fragment_error_empty_password_text)
                    )
                }
            )
        }

    }

    private fun launchSignInFlow(login: String, password: String) {
        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        runDestination(this, R.id.scorersFragment)
                    } else {
                        constraintLayoutLoginFragment.showSnackBar(getString(R.string.login_fragment_error_invalid_login_or_password_text))
                    }
                }
        }
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (loginViewModel.hasNetworkConnection(connectivityManager)) {
            launchSignInFlow(
                textEditUserNameLoginFragment.text.toString().trim(),
                textEditPasswordLoginFragment.text.toString().trim()
            )
        } else {
            constraintLayoutLoginFragment.showSnackBar(getString(R.string.internet_unavailable_text))
        }
    }

    private fun observeAuthenticationState() {
        loginViewModel.authenticationState.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                if (authenticationState == AUTHENTICATED)
                    runDestination(this, R.id.scorersFragment)
            })
    }
}
