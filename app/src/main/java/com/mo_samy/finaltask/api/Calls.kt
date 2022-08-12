package com.mo_samy.finaltask.api

import com.mo_samy.finaltask.models.DataModel
import com.mo_samy.finaltask.models.LoginData
import com.mo_samy.finaltask.models.LoginResponse
import com.mo_samy.finaltask.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Calls {
    @GET("products")
    fun getProducts(): Call<DataModel>
    @GET("products/{id}")
    fun getProductById(@Path("id") productId: Int): Call<Product>
    @POST("login")
    fun login(@Body info: LoginData): Call<LoginResponse>


}