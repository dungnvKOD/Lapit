package com.dung.lapit.main

import com.dung.lapit.App
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainModel {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private  var reference: DatabaseReference

    init {
        reference = firebaseDatabase.reference


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


}