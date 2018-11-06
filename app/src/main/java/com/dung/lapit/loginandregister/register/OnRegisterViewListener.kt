package com.example.dung.applabit.loginandregister.register

interface OnRegisterViewListener {

    fun registerIsSuccess()
    fun registerIsFailed()
    fun addDataFailed()
    fun passwordIsEmpty()
    fun passwordShort()
    fun emailIsEmpty()
    fun emailInvalid() //kiem tra dung dinh dang email
    fun identicalPassword()
    fun upImageFailed()
    fun downLinkFailed()

    fun showProgressBar()
    fun hideProgressBar()
}