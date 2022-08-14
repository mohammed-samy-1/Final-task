package com.mo_samy.finaltask.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mo_samy.finaltask.api.RetrofitFactory
import com.mo_samy.finaltask.models.DataX
import com.mo_samy.finaltask.models.SignUpResponse
import com.mo_samy.finaltask.models.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val mData:MutableLiveData<DataX> = MutableLiveData()
    val data :LiveData<DataX> get() = mData
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = errorMessage
    fun signUp(data :SignupData){
        val conn = RetrofitFactory.call
        val call = conn.signUp(data)
        call.enqueue(object :Callback<SignUpResponse>{
            override fun onResponse(
                call: Call<SignUpResponse>?,
                response: Response<SignUpResponse>?
            ) {
                if (null != response){
                    when(response.code()){
                        200 -> mData.postValue(response.body().data)
                        500 -> errorMessage.postValue("the email already exist")
                        else -> errorMessage.postValue(response.message())
                    }
                }else{
                    errorMessage.postValue("no response from the server")
                }

            }

            override fun onFailure(call: Call<SignUpResponse>?, t: Throwable?) {
                errorMessage.postValue(t?.localizedMessage)
            }

        })
    }
}

