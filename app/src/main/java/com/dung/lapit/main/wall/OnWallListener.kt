package com.dung.lapit.main.wall

import android.graphics.drawable.Drawable
import com.example.dung.applabit.Model.ImageList

interface OnWallListener {

    fun onLoadImageSuccess(drawable: Drawable?)
    fun onLoadImageFailed()

    fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String)
    fun onLoadDataFailed()

    fun onLoadListImageSuccess(image: ImageList)
    fun onLoadListImageFailed()

    fun onAddImageSuccess(image: ImageList)
    fun onAddImageFailed()

    fun isLikeCallBack(boolean: Boolean)
    fun isUnLikeCallBack(boolean: Boolean)

}