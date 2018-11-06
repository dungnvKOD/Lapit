package com.example.dung.applabit.loginandregister.register

interface OnRegisterListener {

    fun registerIsSuccess()
    fun registerIsFailed()
    fun addDataFailed()
    fun passwordIsEmpty()
    fun passwordShort()
    fun identicalPassword()
    fun emailIsEmpty()
    fun emailInvalid()

    fun upImageFailed()
    fun downLinkFailed()

}