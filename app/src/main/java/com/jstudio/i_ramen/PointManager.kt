package com.jstudio.i_ramen

import android.content.SharedPreferences
import android.widget.TextView

class PointManager {

    fun getPoint(SharedPreferences: SharedPreferences): Int{
        val editor = SharedPreferences.edit()
        val havePoint = SharedPreferences.getInt("havePoint", 100)


        return havePoint

    }

}