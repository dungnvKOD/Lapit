package com.dung.lapit.main.message.detailmessage

import com.dung.lapit.Model.Message

interface OnMessageFViewListener {

    fun senMessagerSuccess()
    fun senMessagerFailed()

    fun getMessagedSuccess(message: Message)
    fun getMessagedFailed()

    fun showProgressBar()
    fun hideProgressBar()

}