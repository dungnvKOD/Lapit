package com.dung.lapit.main.message.listuser

import com.dung.lapit.Model.Message

interface OnMessageViewListener {

    fun getDataSuccess(messages: ArrayList<Message>)
    fun getDataFailed()

}