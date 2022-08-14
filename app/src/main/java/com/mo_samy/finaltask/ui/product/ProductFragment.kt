package com.mo_samy.finaltask.ui.product

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.helpers.Helpers
import com.mo_samy.finaltask.models.Data

class ProductFragment : Fragment() {
    private lateinit var img: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtPrice: TextView
    private lateinit var btnBuy: Button
    private lateinit var viewModel: ProductViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var cl: ConstraintLayout
    private lateinit var error: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        val v = inflater.inflate(R.layout.product_fragment, container, false)
        initViews(v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProduct()
        viewModel.product.observe(this.viewLifecycleOwner) {
            initData(it)
        }
        viewModel.errorLiveData.observe(this.viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            error.visibility = View.VISIBLE
            Snackbar.make(view, it, Snackbar.LENGTH_INDEFINITE).setAction("retry") {
                getProduct()
                progressBar.visibility = View.GONE
                error.visibility = View.VISIBLE
            }.show()
        }
        btnBuy.setOnClickListener {
            Toast.makeText(requireContext(), "Order has been placed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getProduct() {
        val args = arguments
        if (args != null) {
            val id = args.getInt(Helpers.ID_FLAG, -1)
            if (id != -1) {
                viewModel.getProduct(id)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData(data: Data) {
        Glide.with(this)
            .asBitmap()
            .placeholder(R.drawable.placeholder)
            .load(data.image)
            .into(img)
        txtName.text = data.name
        txtPrice.text = "${data.price}\$"
        progressBar.visibility = View.GONE
        cl.visibility = View.VISIBLE
        error.visibility = View.GONE

    }

    private fun initViews(v: View) {
        img = v.findViewById(R.id.img_product)
        txtName = v.findViewById(R.id.txt_product_name)
        txtPrice = v.findViewById(R.id.txt_product_price)
        btnBuy = v.findViewById(R.id.btn_buy)
        cl = v.findViewById(R.id.cl_product)
        progressBar = v.findViewById(R.id.progressBar3)
        error = v.findViewById(R.id.error_layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.product.removeObservers(this)
        viewModel.errorLiveData.removeObservers(this)
    }


}