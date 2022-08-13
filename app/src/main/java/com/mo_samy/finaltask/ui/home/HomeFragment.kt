package com.mo_samy.finaltask.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.adapter.ProductsAdapter
import com.mo_samy.finaltask.helpers.Helpers
import com.mo_samy.finaltask.models.Data

class HomeFragment : Fragment() {
    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtName: TextView
    private lateinit var txtLogout: TextView
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_fragment, container, false)
        initViews(v)
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getProduct()
        viewModel.productsLD.observe(this.viewLifecycleOwner) {
            initRV(it)
        }
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences(Helpers.SP_FLAG, 0)
        val name = sharedPreferences.getString(Helpers.NAME_FLAG, "No name")
        txtName.text = name
        txtLogout.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun initRV(it: MutableList<Data>) {
        rvProducts.adapter = ProductsAdapter(this.requireContext(), it)
        rvProducts.layoutManager = GridLayoutManager(this.requireContext(), 2)
        rvProducts.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

    }

    private fun initViews(v: View) {
        rvProducts = v.findViewById(R.id.rv_products)
        progressBar = v.findViewById(R.id.progressBar)
        txtName = v.findViewById(R.id.txt_username)
        txtLogout = v.findViewById(R.id.txt_logout)

    }


}