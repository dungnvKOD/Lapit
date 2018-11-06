package com.example.dung.applabit.loginandregister.login

import android.text.TextUtils
import com.dung.lapit.App

import com.example.dung.applabit.conmon.Constant
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginModel(private val onLoginListener: OnLoginListener) {

    companion object {
        const val TAG = "LoginModel"
    }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference

    init {
        reference = firebaseDatabase.reference
    }

    fun checkLogin(email: String, password: String) {
        val emailType = email.matches(Constant.EMAIL_TYPE.toRegex())

        if (TextUtils.isEmpty(email)) {
            onLoginListener.emailIsEmpty()
            return
        }
        if (TextUtils.isEmpty(password)) {
            onLoginListener.passwordIsEmpty()
            return
        }
        if (!emailType) {//kiem tra email hop le
            onLoginListener.emailInvalid()
            return
        }
        login(email, password)
    }

    fun login(email: String, password: String) {
        auth.signOut()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                getGender()
            } else {
                onLoginListener.loginIsFailed()
            }
        }
    }

    private fun getGender() {

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                App.getInsatnce().isGender = p0.child("Genders").child(auth.currentUser!!.uid).value as Boolean

                onLoginListener.loginIsSuccess()

                //sau khi ket thuc kiem tra danh nhap va lay gioi  tinh thi phai dong luong lai
                reference.removeEventListener(this)

            }
        })


    }
}