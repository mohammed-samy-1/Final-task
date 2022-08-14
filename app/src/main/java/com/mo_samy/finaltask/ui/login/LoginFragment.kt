package com.mo_samy.finaltask.ui.login

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.helpers.Helpers
import com.mo_samy.finaltask.helpers.Helpers.Companion.LOGIN_FLAG
import com.mo_samy.finaltask.helpers.Helpers.Companion.SP_FLAG
import com.mo_samy.finaltask.helpers.Helpers.Companion.validateEmail
import com.mo_samy.finaltask.helpers.Helpers.Companion.validatePass

class LoginFragment : Fragment() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView
    private lateinit var shardedPreferences: SharedPreferences
    private lateinit var pd: ProgressDialog

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.login_fragment, container, false)
        shardedPreferences = requireActivity().getSharedPreferences(SP_FLAG, 0)
        pd = ProgressDialog(requireContext())
        initViews(v)
        return v
    }

    private fun initViews(v: View) {
        etEmail = v.findViewById(R.id.editTextTextEmailAddress)
        etPass = v.findViewById(R.id.et_pass_sign_up)
        btnLogin = v.findViewById(R.id.button)
        tvSignUp = v.findViewById(R.id.tv_signup)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        viewModel.response.observe(this.viewLifecycleOwner) {
            shardedPreferences.edit().putString(Helpers.NAME_FLAG, it.name).apply()

            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
                    shardedPreferences.edit().putBoolean(LOGIN_FLAG, true).apply()
                    pd.dismiss()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }
        viewModel.errorLiveData.observe(this.viewLifecycleOwner) {
            pd.dismiss()
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
        tvSignUp.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        btnLogin.setOnClickListener {
            pd.setTitle("logging in...")
            pd.setMessage("please wait")
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString()
            if (email.validateEmail() and password.validatePass()) {
                viewModel.login(email, password)
                pd.show()
            } else {
                if (!email.validateEmail()) {
                    etEmail.error = "the email is invalid"
                }
                if (!password.validatePass()) {
                    etPass.error = "the password must be at least 8 characters"
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.errorLiveData.removeObservers(this)
        viewModel.response.removeObservers(this)
    }


}