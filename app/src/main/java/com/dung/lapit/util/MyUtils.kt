package com.example.dung.applabit.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MyUtils {


    companion object {
        //        const val TYPE_DATE_D_M_Y = "d-M-Y"
        const val TYPE_DATE_D_M_YYYY = "d-M-yyyy"
        const val TYPE_DATE_HH_MM_a = "hh:mm a"
        const val TYPE_DATE_HH_MM = "hh:mm"
        const val TYPE_DATE_FULL = "E, d/M/yyy hh:mm:ss a"
    }


    //
    @SuppressLint("SimpleDateFormat")
    fun convertTime(time: Long, type: String): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(type)
        val date = Date(time)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    /**
     *  lay thoi gian hien tai
     */
    fun timeHere(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }


    /**
     *  tinh khoang cach a = sin² (Δφ / 2) + cos φ 1 ⋅ cos φ 2 ⋅ sin² (Δλ / 2)
     *  c = 2 ⋅ atan2 (√ a , √ (1 − a) )
     *  d = R ⋅ c
     *  φ là vĩ độ, λ là kinh độ, R là bán kính của trái đất (bán kính trung bình = 6,371km);
     *
     */

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta)))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60.0 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * 180.0 / Math.PI
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    /**
     *  lay dia danh ...
     */

    fun hereLocation(lat: Double, lon: Double, context: Context): String {
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


}