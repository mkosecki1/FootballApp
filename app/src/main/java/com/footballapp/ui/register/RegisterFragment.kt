package com.footballapp.ui.register

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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.register_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val registerViewModel by viewModel<RegisterViewModel>()
    private val firebaseAuth by inject<FirebaseAuth>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginDataChange()

        buttonRegisterFragment.setOnClickListener {
            registerViewModel.setupRegisterLoginData(
                textEditUserNameRegisterFragment.text.toString().trim(),
                textEditPasswordRegisterFragment.text.toString().trim()
            )
            checkInternetConnection()
            view.hideKeyboard()
        }

        backArrowRegisterFragment.setOnClickListener {
            runDestination(this, R.id.loginFragment)
        }
    }

    private fun observeLoginDataChange() {
        with(registerViewModel) {
            isRegisterLoginFormatInvalid.observe(
                viewLifecycleOwner,
                Observer { login ->
                    textInputUserNameRegisterFragment.showErrorMassage(
                        login,
                        getString(R.string.register_fragment_error_invalid_login_format_text)
                    )
                })

            isRegisterPasswordFormatInvalid.observe(
                viewLifecycleOwner, Observer { password ->
                    textInputPasswordRegisterFragment.showErrorMassage(
                        password,
                        getString(R.string.register_fragment_error_invalid_password_format_text)
                    )
                }
            )
        }
    }

    private fun launchRegisterFlow(login: String, password: String) {
        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
            firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        runDestination(this, R.id.scorersFragment)
                    } else {
                        constraintLayoutRegisterFragment.showSnackBar(getString(R.string.register_fragment_error_user_creation_unable))
                    }
                }
        }
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (registerViewModel.hasNetworkConnection(connectivityManager)) {
            launchRegisterFlow(
                textEditUserNameRegisterFragment.text.toString().trim(),
                textEditPasswordRegisterFragment.text.toString().trim()
            )
        } else {
            constraintLayoutRegisterFragment.showSnackBar(getString(R.string.internet_unavailable_text))
        }
    }
}
