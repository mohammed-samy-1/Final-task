package com.mo_samy.finaltask.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mo_samy.finaltask.api.RetrofitFactory
import com.mo_samy.finaltask.models.Data
import com.mo_samy.finaltask.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val productML :MutableLiveData<Data> = MutableLiveData()
    val product:LiveData<Data> get() = productML
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = errorMessage
    fun getProduct(id:Int){
        val conn = RetrofitFactory.call
        val call = conn.getProductById(id)
        call.enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>?, response: Response<Product>?) {
                if (response != null ){
                    when(response.code()){
                        200-> productML.postValue(response.body().data)
                        else -> errorMessage.postValue(response.message())
                    }
                }else{
                    errorMessage.postValue("no response from the server")
                }
            }

            override fun onFailure(call: Call<Product>?, t: Throwable?) {
                if (t != null){
                    errorMessage.postValue(t.localizedMessage)
                }else{
                    errorMessage.postValue("unknown error")
                }
            }

        })
    }
}