package com.dung.lapit.main.friend

import com.dung.lapit.Model.User

interface OnFriendPresenetr {


    fun onGetVisitSuccess(users: ArrayList<User>)
    fun onGetVisitFailed()

}