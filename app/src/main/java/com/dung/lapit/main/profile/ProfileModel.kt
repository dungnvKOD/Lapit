package com.example.dung.applabit.main.profile

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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class ProfileModel(private val onProfileListener: OnProfileListener, private val context: Context?) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference
    private var storage: FirebaseStorage
    private var srf: StorageReference
    private var check = false

    companion object {
        const val TAG = "ProfileModel"
    }

    init {
        reference = firebaseDatabase.reference
        storage = FirebaseStorage.getInstance()
        srf = storage.reference
        getData()
        getListImage()

    }

    fun addImageList(uri: String, time: Long, check: Boolean) {
        this.check = check
        val nameFile = auth.currentUser!!.uid + "$time"

        val line = srf.child("Images").child(nameFile).putFile(Uri.parse(uri))
        line.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                srf.child("Images").child(nameFile).downloadUrl.addOnSuccessListener { uri: Uri? ->
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

    fun getListImage() {

        reference.child("Images").child(auth.currentUser!!.uid)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                    if (check) {
                        onProfileListener.onAddImageFailed()
                        check = false
                    } else {
                        onProfileListener.onLoadListImageFailed()
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
                        onProfileListener.onAddImageSuccess(image)
                        check = false
                    } else {
                        onProfileListener.onLoadListImageSuccess(image)
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })
    }

    fun getData() {
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                onProfileListener.onLoadDataFailed()
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
                    val viTri = hereLocation(user.latitude, user.longitude)
                    Log.d(TAG, "$viTri ${user.latitude}  ${user.longitude}")
                    onProfileListener.onLoadDataSuccess(user.name!!, ngaySinh, gioiTinh, viTri)
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
                    val viTri = hereLocation(user.latitude, user.longitude)
                    Log.d(TAG, "$viTri ${user.latitude}  ${user.longitude}")
                    onProfileListener.onLoadDataSuccess(user.name!!, ngaySinh, gioiTinh, viTri)
                    loadImage(user.imageAvatarURL)

                }
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
                    onProfileListener.onLoadImageSuccess(resource)

                }
            })
    }
}