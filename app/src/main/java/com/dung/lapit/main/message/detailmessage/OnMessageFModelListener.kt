package com.dung.lapit.main.message.detailmessage

import com.dung.lapit.Model.Message

interface OnMessageFModelListener {
    fun senMessagerSuccess()
    fun senMessagerFailed()

    fun getMessagedSuccess(message: Message)
    fun getMessagedFailed()

    fun updateMessage(message: Message)

}