package com.dung.lapit.main.wall

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.dung.applabit.Model.ImageList
import com.example.dung.applabit.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class WallPrecenter(val onWallViewListener: OnWallViewListener, val context: Context) : OnWallListener {

    private val wallModel: WallModel = WallModel(context, this)

    init {
        onWallViewListener.showProgressBarListImage()
        onWallViewListener.showProgressBarAvatar()

    }

    /**
     *  like
     */

    fun like(user: User, reference: DatabaseReference, image: ImageView, isLike: Boolean) {
        wallModel.like(user, reference, image, isLike)
    }

    /**
     *  add image
     */

    fun getMyInfo(reference: DatabaseReference, auth: FirebaseAuth) {
        wallModel.getMyInfo(reference, auth)
    }

    /**
     *  get list Image
     */

    fun getListImage(reference: DatabaseReference, uid: String) {
        wallModel.getListImage(reference, uid)

    }

    fun addImageList(
        uri: String,
        time: Long,
        boolean: Boolean,
        auth: FirebaseAuth,
        reference: DatabaseReference,
        storageReference: StorageReference
    ) {
        onWallViewListener.showDialogAddImage()
        wallModel.addImageList(uri, time, boolean, auth, storageReference, reference)
    }

    override fun onAddImageSuccess(image: ImageList) {
        onWallViewListener.onAddImageSuccess(image)
        onWallViewListener.hideDialogAddImage()
    }

    override fun onAddImageFailed() {
        onWallViewListener.hideDialogAddImage()

    }

    /**
     *  like callback
     */

    override fun isLikeCallBack(boolean: Boolean) {
        onWallViewListener.isLikeCallBack(boolean)
    }

    override fun isUnLikeCallBack(boolean: Boolean) {
        onWallViewListener.isUnLikeCallBack(boolean)
    }

    /**
     * get List image
     */
    override fun onLoadListImageSuccess(image: ImageList) {
        onWallViewListener.onLoadListImageSuccess(image)
        onWallViewListener.hideProgressBarListImage()


    }

    override fun onLoadListImageFailed() {
        onWallViewListener.onLoadListImageFailed()
        onWallViewListener.hideProgressBarListImage()
    }

    override fun onLoadImageSuccess(drawable: Drawable?) {
        onWallViewListener.hideProgressBarAvatar()
        onWallViewListener.onLoadImageSuccess(drawable)
    }

    override fun onLoadImageFailed() {
        onWallViewListener.hideProgressBarAvatar()
        onWallViewListener.onLoadImageFailed()
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String) {
        onWallViewListener.onLoadDataSuccess(name, namSinh, gioiTinh, viTri)
    }

    override fun onLoadDataFailed() {
        onWallViewListener.onLoadDataFailed()

    }
}