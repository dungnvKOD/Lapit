package com.dung.lapit.main.friend

import com.dung.lapit.Model.User

class FriendPresenter(val onFriendPresenetr: OnFriendPresenetr) : OnFriendModel {


    private val friendModel = FriendModel(this)


    override fun onGetVisitSuccess(users: ArrayList<User>) {
        onFriendPresenetr.onGetVisitSuccess(users)
    }

    override fun onGetVisitFailed() {
        onFriendPresenetr.onGetVisitFailed()
    }
}