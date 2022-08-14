package com.mo_samy.finaltask.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.adapter.ProductsAdapter
import com.mo_samy.finaltask.helpers.Helpers
import com.mo_samy.finaltask.models.Data

class HomeFragment : Fragment() , ProductsAdapter.OnItemClicked{
    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtName: TextView
    private lateinit var txtLogout: TextView
    private lateinit var viewModel: HomeViewModel
    private lateinit var error :LinearLayout

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
        viewModel.errorLiveData.observe(this.viewLifecycleOwner){
            if(it != null) {
                error.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                rvProducts.visibility = View.GONE
                Snackbar.make(view, it, Snackbar.LENGTH_INDEFINITE).setAction("retry") {
                    error.visibility = View.GONE
                    rvProducts.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    viewModel.getProduct()
                }.show()
            }
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
        rvProducts.adapter = ProductsAdapter(this.requireContext(), it, this)
        rvProducts.layoutManager = GridLayoutManager(this.requireContext(), 2)
        rvProducts.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        error.visibility = View.GONE

    }

    private fun initViews(v: View) {
        rvProducts = v.findViewById(R.id.rv_products)
        progressBar = v.findViewById(R.id.progressBar)
        txtName = v.findViewById(R.id.txt_username)
        txtLogout = v.findViewById(R.id.txt_logout)
        error = v.findViewById(R.id.error_layout_home)

    }

    override fun onClick(id: Int) {
        val b = Bundle()
        b.putInt(Helpers.ID_FLAG, id)
        findNavController().navigate(R.id.action_homeFragment_to_productFragment, b)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.productsLD.removeObservers(this)
        viewModel.errorLiveData.removeObservers(this)
    }

}