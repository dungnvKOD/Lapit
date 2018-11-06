package com.example.dung.applabit.main.profile

import android.graphics.drawable.Drawable
import com.example.dung.applabit.Model.ImageList

interface OnProfileListener {

    fun onLoadImageSuccess(drawable: Drawable?)
    fun onLoadImageFailed()

    fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String)
    fun onLoadDataFailed()

    fun onLoadListImageSuccess(image: ImageList)
    fun onLoadListImageFailed()

    fun onAddImageSuccess(image: ImageList)
    fun onAddImageFailed()


}