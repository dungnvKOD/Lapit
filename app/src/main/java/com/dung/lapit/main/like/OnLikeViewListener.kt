package com.dung.lapit.main.like

import com.dung.lapit.Model.User

interface OnLikeViewListener {

    fun getDataLikeSuccess(users: ArrayList<User>)
    fun getDataLikeOnFailed()
}