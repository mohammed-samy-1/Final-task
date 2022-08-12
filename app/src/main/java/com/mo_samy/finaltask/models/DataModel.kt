package com.mo_samy.finaltask.models
import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<Data>
)