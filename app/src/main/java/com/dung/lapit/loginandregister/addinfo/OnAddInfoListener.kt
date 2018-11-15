package com.example.dung.applabit.loginandregister.addinfo

import com.dung.lapit.Model.User

interface OnAddInfoListener {

    fun onNameIsEmpty()
    fun onInfoSuccess(user: User)
    fun onInfoFailer()
    fun onIsPermisstionGranted()
    fun onIsPermisstionNotGranted()

    fun onUriIsempty()

}