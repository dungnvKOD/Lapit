package com.dung.lapit.main

import android.content.Context

class MainPresenter(context: Context) {

    private val mainModel = MainModel(context)
    fun insertStatus(boolean: Boolean) {
        mainModel.insertStatus(boolean)
    }

    fun insertLocation(lat: Double, lon: Double) {

        mainModel.insertLocation(lat, lon)
    }
}