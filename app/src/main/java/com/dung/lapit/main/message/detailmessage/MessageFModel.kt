package com.dung.lapit.main.message.detailmessage

import android.net.Uri
import android.util.Log
import com.dung.lapit.App
import com.dung.lapit.Model.Message
import com.dung.lapit.Model.User
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MessageFModel(val onMessageFModelListener: OnMessageFModelListener, friendUser: User) {

    companion object {
        const val TAG = "MessageFModel"
    }


    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference


    init {
        getMessaged(friendUser)

    }

    fun senMessage(friendUser: User, message: Message) {
        /**
         *  quy định iduser nam sẽ đứng trước iduser sẽ đứng sau
         *
         */

        val myID: String = auth.currentUser!!.uid
        val friendID: String = friendUser.idUser!!

        /**
         *  user hiện tại là nam
         */
        if (App.getInsatnce().isGender) {
            val nodeID: String = "${myID}_$friendID".trim()
            insertMessage(nodeID, message)

        } else {
            /**
             *  user hien tai dang la nu
             *
             */
            val nodeID: String = "${friendID}_$myID".trim()
            insertMessage(nodeID, message)
        }
    }

    private fun insertMessage(nodeID: String, message: Message) {


        val idImage: String = "${nodeID}_${MyUtils().timeHere()}"

        //kiem tra image co null hay khong

        if (message.image != null) {
            Log.d(TAG, "${message.image} ...")
            val line = storageReference.child("Messaged").child(idImage).putFile(Uri.parse(message.image))

            line.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageReference.child("Messaged").child(idImage).downloadUrl.addOnSuccessListener { uri: Uri? ->
                        message.image = uri.toString()
                        Log.d(TAG, "${message.image}...link")
                        /**
                         *
                         *  day data len database
                         *
                         */
                        message.image = null
                        Log.d(TAG, "${message.image}...S")

                        onMessageFModelListener.senMessagerSuccess()

                    }.addOnFailureListener {
                        message.image = null
                        Log.d(TAG, "${message.image}...F")
                        onMessageFModelListener.senMessagerFailed()
                    }
                }
            }
        } else {
            /**
             *  neu khong co anh thi gui len data base luon
             */
            Log.d(TAG, "image null ...")
            reference.child("Messaged").child(nodeID).push().setValue(message)
            onMessageFModelListener.senMessagerSuccess()

        }
    }

    private fun getMessaged(friendUser: User) {
        /**
         *  neu la nam  thi cast chuoi lay phan dau vva so sanh
         *
         */
        val myID: String = auth.currentUser!!.uid
        val friendID: String = friendUser.idUser!!

        if (App.getInsatnce().isGender) {
            //nam
            val nodeID: String = "${myID}_$friendID"
            getData(nodeID)

        } else {
            //nu
            val nodeID: String = "${friendID}_$myID"
            getData(nodeID)

        }
    }

    fun getData(nodeID: String) {

        reference.child("Messaged").child(nodeID).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val message: Message = p0.getValue(Message::class.java)!!
                onMessageFModelListener.getMessagedSuccess(message)

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message: Message = p0.getValue(Message::class.java)!!
                onMessageFModelListener.getMessagedSuccess(message)

            }

            override fun onChildRemoved(p0: DataSnapshot) {


            }
        })


    }

}