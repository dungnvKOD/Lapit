package com.example.dung.applabit.loginandregister.addinfo

import com.dung.lapit.Model.User

interface OnInfoViewListener {

    fun onInfoSuccess(user: User)
    fun onInfoFailer()
    fun onNameIsEmpty()

    fun onIsPermisstionGranted()
    fun onIsPermisstionNotGranted()

    fun onUriIsempty()
}