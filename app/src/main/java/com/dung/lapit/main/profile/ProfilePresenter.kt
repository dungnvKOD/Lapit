package com.example.dung.applabit.main.profile

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.dung.applabit.Model.ImageList

class ProfilePresenter(
    private val onProfileViewListener: OnProfileViewListener,
    private val context: Context
) :
    OnProfileListener {


    private val profileModel: ProfileModel = ProfileModel(this, context)

    init {
        onProfileViewListener.showProgressBarListImage()
        onProfileViewListener.showProgressBarAvatar()
    }

    /**
     *  add image
     */
    fun addImageList(uri: String, time: Long, boolean: Boolean) {
        onProfileViewListener.showDialogAddImage()
        profileModel.addImageList(uri, time, boolean)
    }

    override fun onAddImageSuccess(image: ImageList) {
        onProfileViewListener.onAddImageSuccess(image)
        onProfileViewListener.hideDialogAddImage()
    }

    override fun onAddImageFailed() {
        onProfileViewListener.hideDialogAddImage()
    }

    /**
     * get List image
     */
    override fun onLoadListImageSuccess(image: ImageList) {
        onProfileViewListener.onLoadListImageSuccess(image)
        onProfileViewListener.hideProgressBarListImage()


    }

    override fun onLoadListImageFailed() {
        onProfileViewListener.onLoadListImageFailed()
        onProfileViewListener.hideProgressBarListImage()
    }


    override fun onLoadImageSuccess(drawable: Drawable?) {
        onProfileViewListener.hideProgressBarAvatar()
        onProfileViewListener.onLoadImageSuccess(drawable)
    }

    override fun onLoadImageFailed() {
        onProfileViewListener.hideProgressBarAvatar()
        onProfileViewListener.onLoadImageFailed()
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String) {
        onProfileViewListener.onLoadDataSuccess(name, namSinh, gioiTinh, viTri)
    }

    override fun onLoadDataFailed() {
        onProfileViewListener.onLoadDataFailed()

    }

}