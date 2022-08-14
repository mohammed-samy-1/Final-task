package com.mo_samy.finaltask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mo_samy.finaltask.api.RetrofitFactory
import com.mo_samy.finaltask.models.DataX
import com.mo_samy.finaltask.models.LoginData
import com.mo_samy.finaltask.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private var responseMLD:MutableLiveData<DataX> = MutableLiveData()
    val response :LiveData<DataX> get() = responseMLD
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
                if (response != null){
                    when(response.code()){
                        200-> {
                            responseMLD.postValue(response.body().data)
                        }
                        else ->{
                            errorMessage.postValue(response.message())
                        }
                    }
                }

            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                if (t != null)
                    errorMessage.postValue(t.localizedMessage)
            }

        })
    }
}