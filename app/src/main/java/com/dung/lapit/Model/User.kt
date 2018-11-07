package com.example.dung.applabit.Model

import java.io.Serializable

class User : Serializable {
    var idUser: String? = null
    var name: String? = null
    var ngaySinh: Long = -1
    var imageAvatarURL: String? = null
    var discribe: String? = null       //mo ta...
    var gioiTinh: Boolean = false
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var status: Boolean = false

    constructor()


    constructor(
        idUser: String?,
        name: String?,
        ngaySinh: Long,
        imageAvatarURL: String?,
        discribe: String?,
        gioiTinh: Boolean,
        latitude: Double,
        longitude: Double,
        status: Boolean
    ) {
        this.idUser = idUser
        this.name = name
        this.ngaySinh = ngaySinh
        this.imageAvatarURL = imageAvatarURL
        this.discribe = discribe
        this.gioiTinh = gioiTinh
        this.latitude = latitude
        this.longitude = longitude
        this.status = status
    }


}