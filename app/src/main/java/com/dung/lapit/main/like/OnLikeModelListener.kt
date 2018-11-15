package com.dung.lapit.main.like

import com.dung.lapit.Model.User

interface OnLikeModelListener {

    fun getDataLikeSuccess(user: ArrayList<User>)
    fun getDataLikeOnFailed()

}