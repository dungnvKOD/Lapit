package com.example.dung.applabit.loginandregister.login

interface OnLoginListener {

    fun emailIsEmpty()
    fun passwordIsEmpty()
    fun loginIsSuccess()
    fun emailInvalid()
    fun loginIsFailed()
}