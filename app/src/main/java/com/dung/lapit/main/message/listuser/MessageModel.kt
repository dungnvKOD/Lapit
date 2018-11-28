package com.dung.lapit.main.message.listuser

import android.util.Log
import com.dung.lapit.Model.Message
import com.google.firebase.database.*

class MessageModel(val onMessageModelListener: OnMessageModelListener) {
    private val reference = FirebaseDatabase.getInstance().reference

    companion object {
        val TAG = "MessageModel"
    }

    fun getData() {
        var messages: ArrayList<Message> = ArrayList()
        Log.d(TAG, "ok...")
        reference.child("Messaged").orderByChild("time").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                messages.clear()
                for (data: DataSnapshot in p0.children) {
                    val message: Message = data.getValue(Message::class.java)!!
                    messages.add(message)
                }

                onMessageModelListener.getDataSuccess(messages)
            }
        })
    }
}