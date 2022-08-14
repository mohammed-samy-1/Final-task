package com.mo_samy.finaltask.ui.signup

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.helpers.Helpers
import com.mo_samy.finaltask.helpers.Helpers.Companion.validateEmail
import com.mo_samy.finaltask.helpers.Helpers.Companion.validatePass
import com.mo_samy.finaltask.models.SignupData

class SignupFragment : Fragment() {


    private lateinit var viewModel: SignupViewModel
    private lateinit var etName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPass : EditText
    private lateinit var btnSignUp : Button
    private lateinit var shardedPreferences: SharedPreferences
    private lateinit var pd :ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v =  inflater.inflate(R.layout.signup_fragment, container, false)
        initViews(v)
        shardedPreferences = requireActivity().getSharedPreferences(Helpers.SP_FLAG, 0)
        return v
    }

    private fun initViews(view: View) {
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.et_email_signup)
        etPass = view.findViewById(R.id.et_pass_sign_up)
        btnSignUp = view.findViewById(R.id.btn_sign_up)
        pd = ProgressDialog(requireContext())
        pd.setTitle("Creating account")
        pd.setMessage("Please wait")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        btnSignUp.setOnClickListener{
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString()
            if (name.isNotEmpty() and email.validateEmail() and pass.validatePass()){
                viewModel.signUp(SignupData(name , email , pass))
                pd.show()
            }else{
                if (name.isEmpty()){
                    etName.error = "Please enter a name"
                }
                if (!email.validateEmail()){
                    etEmail.error = "Invalid E-mail format"
                }
                if (!pass.validatePass()){
                    etPass.error = "Password must be at least 8 characters"
                }
            }
        }
        viewModel.data.observe(this.viewLifecycleOwner ){
            shardedPreferences.edit().putString(Helpers.NAME_FLAG , it.name).apply()
            shardedPreferences.edit().putBoolean(Helpers.LOGIN_FLAG, true).apply()
            pd.dismiss()
            view.findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
        }
        viewModel.errorLiveData.observe(this.viewLifecycleOwner){
            pd.dismiss()
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.data.removeObservers(this)
        viewModel.errorLiveData.removeObservers(this)
    }


}