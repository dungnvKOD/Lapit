package com.dung.lapit.main.findfriend

import com.dung.lapit.Model.User

interface OnFindFriendViewListenr {
    fun getFriendSuccess(users: User)
    fun getFriendFailed(users: User)

    fun updateFriendSuccess(user: User)

    fun showProgressBar()
    fun hideProgressBar()

}