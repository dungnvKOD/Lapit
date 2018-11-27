package com.dung.lapit.main.message.listuser

import com.dung.lapit.Model.Message

class MessagePresenter(val onMessageViewListener: OnMessageViewListener) : OnMessageModelListener {

    private val messageModel = MessageModel(this)
    fun getData() {
        messageModel.getData()
    }

    override fun getDataSuccess(message: Message) {
        onMessageViewListener.getDataSuccess(message)
    }

    override fun getDataFailed() {
        onMessageViewListener.getDataFailed()

    }
}