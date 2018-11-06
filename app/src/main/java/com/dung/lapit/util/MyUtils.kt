package com.example.dung.applabit.util

import android.annotation.SuppressLint
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
     *
     */


}