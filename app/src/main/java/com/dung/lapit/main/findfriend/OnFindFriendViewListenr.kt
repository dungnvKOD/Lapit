package com.example.dung.applabit.main.findfriend

import com.example.dung.applabit.Model.User

interface OnFindFriendViewListenr {
    fun getFriendSuccess(users: User)
    fun getFriendFailed(users: User)

    fun updateFriendSuccess(user: User)

    fun showProgressBar()
    fun hideProgressBar()

}