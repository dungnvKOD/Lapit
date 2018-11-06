package com.example.dung.applabit.loginandregister.addinfo

import com.example.dung.applabit.Model.User

interface OnInfoViewListener {

    fun onInfoSuccess(user: User)
    fun onInfoFailer()
    fun onNameIsEmpty()

    fun onIsPermisstionGranted()
    fun onIsPermisstionNotGranted()

    fun onUriIsempty()
}