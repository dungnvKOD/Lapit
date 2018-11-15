package com.example.dung.applabit.main.findfriend

import com.dung.lapit.Model.User


interface OnFindFriendListener {
    fun getFriendSuccess(users: User)
    fun getFriendFailed(users: User)

    fun updateFriendSuccess(user: User)




}