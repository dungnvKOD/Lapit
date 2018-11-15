package com.dung.lapit.main.like

import android.util.Log
import com.dung.lapit.App
import com.dung.lapit.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LikeModel(val onLikeModelListener: OnLikeModelListener) {

    companion object {
        const val TAG = "LikeModel"
    }

    //
    private var reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    init {

        getDataLike()
    }

    fun getDataLike() {

        var users: ArrayList<User> = ArrayList()

        if (App.getInsatnce().isGender) {

//            Log.d(TAG, "${auth.currentUser!!.uid}.....")
            reference.child("UsersMale").child(auth.currentUser!!.uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            onLikeModelListener.getDataLikeOnFailed()
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            users.clear()
                            if (p0.child("like").exists()) {

                                for (data: DataSnapshot in p0.child("like").children) {
                                    val user: User = data.getValue(User::class.java)!!
                                    Log.d(TAG, "${user.idUser}...")
                                    users.add(user)
                                }
                                onLikeModelListener.getDataLikeSuccess(users)

                            } else {
                                onLikeModelListener.getDataLikeOnFailed()

                            }
                        }
                    })
        } else {
            reference.child("UsersFemale").child(auth.currentUser!!.uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            onLikeModelListener.getDataLikeOnFailed()
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            users.clear()
                            if (p0.child("like").exists()) {
                                for (data: DataSnapshot in p0.child("like").children) {
                                    val user = data.child("like").getValue(User::class.java)
                                    users.add(user!!)
                                }
                                onLikeModelListener.getDataLikeSuccess(users)
                            } else {
                                onLikeModelListener.getDataLikeOnFailed()

                            }
                        }
                    })
        }
    }
}