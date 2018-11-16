package com.dung.lapit.main.friend

import com.dung.lapit.Model.User

interface OnFriendModel {


    fun onGetVisitSuccess(users: ArrayList<User>)
    fun onGetVisitFailed()


}