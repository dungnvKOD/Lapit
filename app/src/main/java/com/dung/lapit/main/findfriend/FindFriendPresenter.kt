package com.example.dung.applabit.main.findfriend

import com.dung.lapit.main.findfriend.FindFriendModel
import com.dung.lapit.main.findfriend.OnFindFriendViewListenr
import com.example.dung.applabit.Model.User


class FindFriendPresenter(private val onFindFriendViewListenr: OnFindFriendViewListenr) : OnFindFriendListener {


    private val findFriendModel = FindFriendModel(this)


    init {
        findFriendModel.getDataFindFriend()
        onFindFriendViewListenr.showProgressBar()
    }


    override fun getFriendSuccess(users: User) {
        onFindFriendViewListenr.getFriendSuccess(users)
        onFindFriendViewListenr.hideProgressBar()
    }

    override fun getFriendFailed(users: User) {
        onFindFriendViewListenr.updateFriendSuccess(users)
        onFindFriendViewListenr.hideProgressBar()

    }

    override fun updateFriendSuccess(user: User) {
        onFindFriendViewListenr.updateFriendSuccess(user)

    }


}
