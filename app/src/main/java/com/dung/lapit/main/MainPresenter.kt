package com.dung.lapit.main

class MainPresenter {

    private val mainModel = MainModel()
    fun insertStatus(boolean: Boolean) {
        mainModel.insertStatus(boolean)
    }

    fun insertLocation(lat: Double, lon: Double) {

        mainModel.insertLocation(lat, lon)
    }
}