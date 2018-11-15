package com.dung.lapit.Model

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
//    var like: Boolean = false
//    var visit: Boolean = false




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


//    constructor(
//        idUser: String?,
//        name: String?,
//        ngaySinh: Long,
//        imageAvatarURL: String?,
//        discribe: String?,
//        gioiTinh: Boolean,
//        latitude: Double,
//        longitude: Double,
//        status: Boolean,
//        like: Boolean
//    ) {
//        this.idUser = idUser
//        this.name = name
//        this.ngaySinh = ngaySinh
//        this.imageAvatarURL = imageAvatarURL
//        this.discribe = discribe
//        this.gioiTinh = gioiTinh
//        this.latitude = latitude
//        this.longitude = longitude
//        this.status = status
//        this.like = like
//    }

//    constructor(
//        idUser: String?,
//        name: String?,
//        ngaySinh: Long,
//        imageAvatarURL: String?,
//        discribe: String?,
//        gioiTinh: Boolean,
//        latitude: Double,
//        longitude: Double,
//        status: Boolean,
//        like: Boolean,
//        visit: Boolean
//    ) {
//        this.idUser = idUser
//        this.name = name
//        this.ngaySinh = ngaySinh
//        this.imageAvatarURL = imageAvatarURL
//        this.discribe = discribe
//        this.gioiTinh = gioiTinh
//        this.latitude = latitude
//        this.longitude = longitude
//        this.status = status
//        this.like = like
//        this.visit = visit
//    }




    /**
     *  tinh khoang cach a = sin² (Δφ / 2) + cos φ 1 ⋅ cos φ 2 ⋅ sin² (Δλ / 2)
     *  c = 2 ⋅ atan2 (√ a , √ (1 − a) )
     *  d = R ⋅ c
     *  φ là vĩ độ, λ là kinh độ, R là bán kính của trái đất (bán kính trung bình = 6,371km);
     *
     */
    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta)))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60.0 * 1.1515
        return "%.1f".format(dist) + "km"
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}