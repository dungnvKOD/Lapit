package com.dung.lapit.main.message.listuser

import com.dung.lapit.Model.Message

interface OnMessageViewListener {

    fun getDataSuccess(message: Message)
    fun getDataFailed()

}