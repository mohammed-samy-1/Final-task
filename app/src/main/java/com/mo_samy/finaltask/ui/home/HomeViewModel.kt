package com.mo_samy.finaltask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mo_samy.finaltask.api.RetrofitFactory
import com.mo_samy.finaltask.models.Data
import com.mo_samy.finaltask.models.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val productsMLD: MutableLiveData<ArrayList<Data>> = MutableLiveData()
    val productsLD: LiveData<ArrayList<Data>> get() = productsMLD
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = errorMessage
    fun getProduct() {
        val conn = RetrofitFactory.call
        val call = conn.getProducts()
        call.enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                when (response.code()) {
                    200 -> productsMLD.postValue(response.body().data as ArrayList<Data>)
                    else -> errorMessage.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                errorMessage.postValue(t?.localizedMessage)
            }

        })

    }

}