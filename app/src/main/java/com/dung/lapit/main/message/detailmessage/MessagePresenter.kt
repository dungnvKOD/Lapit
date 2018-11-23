package com.dung.lapit.main.message.detailmessage

import com.dung.lapit.Model.Message
import com.dung.lapit.Model.User

class MessagePresenter(private val onMessageFViewListener: OnMessageFViewListener, friendUser: User) :
    OnMessageFModelListener {


    private val messageModel: MessageFModel = MessageFModel(this, friendUser)

    fun senMessage(friendUser: User, message: Message) {
        messageModel.senMessage(friendUser, message)
    }

    fun getMesssage(fUser: User) {
        messageModel.getMessaged(fUser)

    }

    override fun senMessagerSuccess() {
        onMessageFViewListener.senMessagerSuccess()
    }

    override fun senMessagerFailed() {
        onMessageFViewListener.senMessagerFailed()
    }

    override fun getMessagedSuccess(message: Message) {
        onMessageFViewListener.getMessagedSuccess(message)
    }

    override fun getMessagedFailed() {
        onMessageFViewListener.getMessagedFailed()
    }

    override fun updateMessage(message: Message) {
        onMessageFViewListener.updateMessage(message)
    }
}