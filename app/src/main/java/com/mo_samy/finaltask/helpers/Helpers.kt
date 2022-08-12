package com.mo_samy.finaltask.helpers

class Helpers {
    companion object{
        fun String.validateEmail():Boolean{
            return if(this.isEmpty()){
                false
            }else{
                android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
            }
        }
        fun String.validatePass():Boolean{
            return !(this.isEmpty()||this.length<6)
        }
    }
}