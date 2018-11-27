package com.dung.lapit.main.message.listuser

import android.util.Log
import com.dung.lapit.Model.Message
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MessageModel(val onMessageModelListener: OnMessageModelListener) {
    private val reference = FirebaseDatabase.getInstance().reference

    companion object {
        val TAG = "MessageModel"
    }

    fun getData() {
        Log.d(TAG, "ok...")
        reference.child("Messaged").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                val message: Message = p0.getValue(Message::class.java)!!
                Log.d(TAG, "${message.message}")
                onMessageModelListener.getDataSuccess(message)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message: Message = p0.getValue(Message::class.java)!!
                Log.d(TAG, "${message.message}")
                onMessageModelListener.getDataSuccess(message)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }


        })


    }
}