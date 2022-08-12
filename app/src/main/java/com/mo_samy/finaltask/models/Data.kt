package com.mo_samy.finaltask.models
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("restaurant_id")
    val restaurant_id: Int,
    @SerializedName("image")
    val image: String,
)
