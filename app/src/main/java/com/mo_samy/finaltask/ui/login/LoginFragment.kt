package com.mo_samy.finaltask.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.helpers.Helpers.Companion.validateEmail
import com.mo_samy.finaltask.helpers.Helpers.Companion.validatePass

class LoginFragment : Fragment() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvLogin: TextView

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.login_fragment, container, false)
        initViews(v)
        return v

    }

    private fun initViews(v: View) {
        etEmail = v.findViewById(R.id.editTextTextEmailAddress)
        etPass = v.findViewById(R.id.editTextTextPassword)
        btnLogin = v.findViewById(R.id.button)
        tvLogin = v.findViewById(R.id.tv_signup)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.responseCode.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                200 -> {
                    Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                else -> Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorLiveData.observe(this.viewLifecycleOwner, Observer {
            Toast.makeText(activity,it, Toast.LENGTH_SHORT).show()
        })
        tvLogin.setOnClickListener(View.OnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        })
        btnLogin.setOnClickListener(View.OnClickListener {
            val email = etEmail.text.toString()
            val password = etPass.text.toString()
            if (email.validateEmail() and password.validatePass()) {
                viewModel.login(email, password)
            }else{
                if (!email.validateEmail()){
                    etEmail.error = "the email is invalid"
                }
                if (!password.validatePass()){
                    etPass.error= "the password must be at least 10 characters"
                }
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}