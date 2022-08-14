package com.mo_samy.finaltask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mo_samy.finaltask.R
import com.mo_samy.finaltask.models.Data

class ProductsAdapter(private val context :Context, private var items :MutableList<Data>, var onClick :OnItemClicked)
    : RecyclerView.Adapter<ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_holder,parent,false))
    }
    interface OnItemClicked{
        fun onClick(id: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        Glide.with(context)
            .asBitmap()
            .load(items[position].image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.img)
        holder.name.text = items[position].name
        holder.price.text = "${ items[position].price }$"
        holder.ccl.setOnClickListener {
            onClick.onClick(items[position].id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var  img :ImageView = itemView.findViewById(R.id.img_product_list)
        var  name :TextView = itemView.findViewById(R.id.txt_name_list)
        var  price :TextView = itemView.findViewById(R.id.txt_price_list)
        val ccl : CardView = itemView.findViewById(R.id.parent)
    }

