package com.mo_samy.finaltask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mo_samy.finaltask.api.RetrofitFactory
import com.mo_samy.finaltask.models.LoginData
import com.mo_samy.finaltask.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private var responseCodeMLD:MutableLiveData<Int> = MutableLiveData()
    val responseCode :LiveData<Int> get() = responseCodeMLD
    private var nameMLD:MutableLiveData<String> = MutableLiveData()
    val name :LiveData<String> get() = nameMLD
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = errorMessage
    fun login(email:String , password:String){
        val conn = RetrofitFactory.call
        val call = conn.login(LoginData(email, password))
        call.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>?,
                response: Response<LoginResponse>?
            ) {
                responseCodeMLD.postValue(response!!.code())
                nameMLD.postValue(response.body().data.name)
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                errorMessage.postValue(t!!.localizedMessage)
            }

        })
    }
}