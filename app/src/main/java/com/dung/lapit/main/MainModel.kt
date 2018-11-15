package com.dung.lapit.main

import android.content.Context
import com.dung.lapit.App
import com.dung.lapit.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class MainModel(val context: Context) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference

    init {
        reference = firebaseDatabase.reference

        getMyInfo(reference, auth)
    }

    fun insertStatus(boolean: Boolean) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["status"] = boolean

        if (App.getInsatnce().isGender) {
            reference.child("UsersMale").child(auth.currentUser!!.uid).updateChildren(hashMap)
        } else {
            reference.child("UsersFemale").child(auth.currentUser!!.uid).updateChildren(hashMap)
        }
    }

    fun insertLocation(lat: Double, lon: Double) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["latitude"] = lat
        hashMap["longitude"] = lon
        if (App.getInsatnce().isGender) {
            reference.child("UsersMale").child(auth.currentUser!!.uid).updateChildren(hashMap)
        } else {
            reference.child("UsersFemale").child(auth.currentUser!!.uid).updateChildren(hashMap)
        }
    }


    fun getMyInfo(reference: DatabaseReference, auth: FirebaseAuth) {

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (App.getInsatnce().isGender) {
                    val user: User = p0.child("UsersMale").child(auth.currentUser!!.uid).getValue(User::class.java)!!
                    App.getInsatnce().user = user

                } else {
                    val user: User = p0.child("UsersFemale").child(auth.currentUser!!.uid).getValue(User::class.java)!!
                    App.getInsatnce().user = user
                }
                reference.removeEventListener(this)
            }
        }
        reference.addValueEventListener(valueEventListener)
    }


}