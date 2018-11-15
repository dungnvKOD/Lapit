package com.dung.lapit.main.like

import com.dung.lapit.Model.User

class LikePresenter(val onLikeViewListener: OnLikeViewListener) : OnLikeModelListener {


    private val likeModel = LikeModel(this)

    init {
        likeModel.getDataLike()

    }


    override fun getDataLikeSuccess(user: ArrayList<User>) {
        onLikeViewListener.getDataLikeSuccess(user)
    }

    override fun getDataLikeOnFailed() {
        onLikeViewListener.getDataLikeOnFailed()
    }

}