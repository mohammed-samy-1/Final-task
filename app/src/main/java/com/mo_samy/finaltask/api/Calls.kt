package com.mo_samy.finaltask.api

import com.mo_samy.finaltask.models.*
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
    fun login(@Body data: LoginData): Call<LoginResponse>
    @POST("sign_up")
    fun signUp(@Body data: SignupData):Call<SignUpResponse>


}