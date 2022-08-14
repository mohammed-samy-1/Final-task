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
            return !(this.isEmpty()||this.length<8)
        }
        const val LOGIN_FLAG : String = "IS_LOGGED"
        const val SP_FLAG : String = "login"
        const val NAME_FLAG : String = "userName"
        const val ID_FLAG : String = "ID"
    }
}