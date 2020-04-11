package com.footballapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footballapp.R
import com.footballapp.ext.setErrorText
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

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
        }
    }

    private fun observeLoginDataChange() {
        loginViewModel.isLoginDataValid.observe(
            viewLifecycleOwner,
            Observer { loginData ->
                textInputUserNameLoginFragment.setErrorText(
                    loginData.checkLoginValidation(getString(R.string.login_fragment_error_invalid_format_text))
                )
                textInputPasswordLoginFragment.setErrorText(
                    loginData.checkPasswordValidation(getString(R.string.login_fragment_error_empty_password_text))
                )
            }
        )
    }
}
