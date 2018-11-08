package com.dung.lapit.main.wall

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dung.lapit.App
import com.example.dung.applabit.Model.ImageList
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class WallModel(val context: Context, val onWallListener: OnWallListener) {
    companion object {
        const val TAG = "WallModel"
    }

    private var check = false

    fun addImageList(
            uri: String,
            time: Long,
            check: Boolean,
            auth: FirebaseAuth,
            str: StorageReference,
            reference: DatabaseReference
    ) {
        this.check = check
        val nameFile = auth.currentUser!!.uid + "$time"

        val line = str.child("Images").child(nameFile).putFile(Uri.parse(uri))
        line.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                str.child("Images").child(nameFile).downloadUrl.addOnSuccessListener { uri: Uri? ->
                    val hashMap: HashMap<String, Any> = HashMap()
                    hashMap["url"] = uri.toString()
                    hashMap["time"] = time
                    reference.child("Images").child(auth.currentUser!!.uid).child(nameFile).setValue(hashMap)
                            .addOnSuccessListener { void: Void? ->

                            }.addOnFailureListener { exception: Exception ->

                            }
                }
            }
        }
    }

    /**
     *  like
     */

    fun like(user: User, reference: DatabaseReference, storageReference: StorageReference) {
        val id: String = user.idUser!!
        if (App.getInsatnce().isGender) {

            reference.child("UsersFemale").child(id).child("like").child(id).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap[id] = user
                        reference.child("UsersFemale").child(id).child("like").updateChildren(hashMap)
                        //TODO callback
                        onWallListener.isLikeCallBack()

                    } else {
                        //TODO callback
                        onWallListener.isUnLikeCallBack()
                    }
                }
            })


        } else {
            reference.child("UsersMale").child(id).child("like").child(id).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap[id] = user
                        reference.child("UsersMale").child(id).child("like").updateChildren(hashMap)
                        //TODO callback
                        onWallListener.isLikeCallBack()

                    } else {
                        //TODO callback
                        onWallListener.isUnLikeCallBack()
                    }
                }
            })
        }
    }


    fun getMyInfo(reference: DatabaseReference, auth: FirebaseAuth) {

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                onWallListener.onLoadDataFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (App.getInsatnce().isGender) {

                    val user: User = p0.child("UsersMale").child(auth.currentUser!!.uid).getValue(User::class.java)!!
                    var gioiTinh: String = ""
                    val ngaySinh = MyUtils().convertTime(user.ngaySinh, MyUtils.TYPE_DATE_D_M_YYYY)


                    gioiTinh = if (p0.child("Genders").child(auth.currentUser!!.uid).value as Boolean) {
                        "nam"
                    } else {
                        "nu"
                    }
                    val viTri = hereLocation(App.getInsatnce().latitude, App.getInsatnce().longitude) + ""
                    Log.d(TAG, "$viTri ${user.latitude}  ${user.longitude}")
                    onWallListener.onLoadDataSuccess(user.name!!, ngaySinh, gioiTinh, viTri)
                    loadImage(user.imageAvatarURL)

                } else {
                    val user: User = p0.child("UsersFemale").child(auth.currentUser!!.uid).getValue(User::class.java)!!
                    var gioiTinh: String = ""
                    val ngaySinh = MyUtils().convertTime(user.ngaySinh, MyUtils.TYPE_DATE_D_M_YYYY)

                    gioiTinh = if (p0.child("Genders").child(auth.currentUser!!.uid).value as Boolean) {
                        "nam"
                    } else {
                        "nu"
                    }
                    val viTri = hereLocation(App.getInsatnce().latitude, App.getInsatnce().longitude)
                    Log.d(TAG, "$viTri ${user.latitude}  ${user.longitude}")
                    onWallListener.onLoadDataSuccess(user.name!!, ngaySinh, gioiTinh, viTri)
                    loadImage(user.imageAvatarURL)

                }

                reference.removeEventListener(this)
            }
        }
        reference.addValueEventListener(valueEventListener)
    }

    private fun hereLocation(lat: Double, lon: Double): String {
        var city = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>
        try {

            addresses = geocoder.getFromLocation(lat, lon, 15)
            if (addresses.isNotEmpty()) {
                for (adr: Address in addresses) {
                    if (adr.locality != null) {
                        city = adr.locality
                        return city
                    }
                }
            }
        } catch (e: Exception) {
        }
        return city
    }

    @SuppressLint("CheckResult")
    private fun loadImage(url: String?) {//thinh toang no bao loi doan nay la do phuong thuc da loi thoi

        if (context == null) {
            return
        }
        Glide.with(context)
                .load(Uri.parse(url)).into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        onWallListener.onLoadImageSuccess(resource)

                    }
                }).onDestroy()
    }

    fun getListImage(reference: DatabaseReference, uid: String) {

        reference.child("Images").child(uid)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                        if (check) {
                            onWallListener.onAddImageFailed()
                            check = false
                        } else {
                            onWallListener.onLoadListImageFailed()
                        }
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    }

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    }

                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                        Log.d(TAG, "Ton tai$p0")
                        val image = p0.getValue(ImageList::class.java)!!
                        if (check) {
                            onWallListener.onAddImageSuccess(image)
                            check = false
                        } else {
                            onWallListener.onLoadListImageSuccess(image)
                        }
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {
                    }
                })
    }

}