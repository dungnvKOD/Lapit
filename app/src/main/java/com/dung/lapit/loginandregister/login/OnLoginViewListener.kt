package com.example.dung.applabit.loginandregister.login

interface OnLoginViewListener {

    fun emailIsEmpty()
    fun passwordIsEmpty()
    fun loginIsSuccess()
    fun emailInvalid()
    fun loginIsFailed()
    fun checkLocationSuccess()
    fun checkLocatiomFailed()

    fun showProgressBar()
    fun hideProgressBar()

}