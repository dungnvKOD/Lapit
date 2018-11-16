package com.dung.lapit.main.friend

import android.util.Log
import com.dung.lapit.App
import com.dung.lapit.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FriendModel(val onFriendModel: OnFriendModel) {
    companion object {

        const val TAG = "FriendModel"
    }

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    init {
        getVisit()
    }

    fun getVisit() {

        var users: ArrayList<User> = ArrayList()

        if (App.getInsatnce().isGender) {
            reference.child("UsersMale").child(auth.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {


                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.child("visit").exists()) {
                            Log.d(TAG, "ton tai...")
                            for (data: DataSnapshot in p0.child("visit").children) {
                                val user = data.getValue(User::class.java)
                                Log.d(TAG, user!!.name.toString() + "...")

                                //TODO dang lm den day

                                users.add(user)
                            }
                            onFriendModel.onGetVisitSuccess(users)
                        } else {
                            Log.d(TAG, "khong ton tai...")
                            onFriendModel.onGetVisitFailed()

                        }
                    }
                })
        } else {
            reference.child("UsersFemale").child(auth.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {


                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.child("visit").exists()) {
                            Log.d(TAG, "ton tai...")
                            for (data: DataSnapshot in p0.children) {
                                val user = data.child("visit").getValue(User::class.java)
                                Log.d(TAG, user!!.name.toString() + "...")


                                users.add(user)


                            }
                            onFriendModel.onGetVisitSuccess(users)
                        } else {
                            Log.d(TAG, "khong ton tai...")

                            onFriendModel.onGetVisitFailed()
                        }
                    }
                })
        }
    }

}