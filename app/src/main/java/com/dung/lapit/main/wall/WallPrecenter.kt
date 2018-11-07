package com.dung.lapit.main.wall

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.dung.applabit.Model.ImageList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class WallPrecenter(val onWallViewListener: OnWallViewListener, val context: Context) : OnWallListener {


    private val wallModel: WallModel = WallModel(context, this)

    init {
        onWallViewListener.showProgressBarListImage()
        onWallViewListener.showProgressBarAvatar()

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
//      onWallViewListener.

    }

    fun addImageList(uri: String, time: Long, boolean: Boolean) {
        onWallViewListener.showDialogAddImage()
//        wallModel.addImageList(uri, time, boolean)
    }

    override fun onAddImageSuccess(image: ImageList) {
        onWallViewListener.onAddImageSuccess(image)
        onWallViewListener.hideDialogAddImage()
    }

    override fun onAddImageFailed() {
        onWallViewListener.hideDialogAddImage()

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