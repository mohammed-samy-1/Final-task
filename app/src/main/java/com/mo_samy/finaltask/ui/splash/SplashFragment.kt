package com.mo_samy.finaltask.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.helpers.Helpers

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences(Helpers.SP_FLAG, 0)
        val isLogged = sharedPreferences.getBoolean(Helpers.LOGIN_FLAG, false)
        Handler(Looper.myLooper()!!).postDelayed(
            {
                if (isLogged) {
                    view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    view.findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            },
            500,
        )
    }


}