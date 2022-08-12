package com.mo_samy.finaltask.models

data class LoginResponse(
    val `data`: DataX,
    val message: String,
    val status: Boolean,
    val token: String
)