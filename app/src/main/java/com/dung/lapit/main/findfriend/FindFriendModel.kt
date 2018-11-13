package com.dung.lapit.main.findfriend

import android.util.Log
import com.dung.lapit.App
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.main.findfriend.OnFindFriendListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FindFriendModel(private val onFindFriendListener: OnFindFriendListener) {

    companion object {
        const val TAG = "FindFriendModel"
    }

    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth
    private var reference: DatabaseReference
    private var users: ArrayList<User>? = ArrayList()

    init {
        reference = firebaseDatabase.reference
        auth = FirebaseAuth.getInstance()
    }

    fun getDataFindFriend() {

        if (App.getInsatnce().isGender) {
            reference.child("UsersFemale").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    onFindFriendListener.updateFriendSuccess(user)
                    Log.d(TAG, user.name + "...")

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    onFindFriendListener.getFriendSuccess(user)
                    Log.d(TAG, user.name + "...")

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })

        } else {
            reference.child("UsersMale").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    onFindFriendListener.updateFriendSuccess(user)
                    Log.d(TAG, user.name + "...")

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    onFindFriendListener.getFriendSuccess(user)
                    Log.d(TAG, user.name + "...")

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })
        }
    }
}