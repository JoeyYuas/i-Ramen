package com.jstudio.i_ramen
import android.content.SharedPreferences

class PointManager {

    fun getPoint(SharedPreferences: SharedPreferences): Int{
        val havePoint = SharedPreferences.getInt("havePoint", 100)

        return havePoint
    }

    fun setPoint(SharedPreferences: SharedPreferences, saveNum:Int){
        val editor = SharedPreferences.edit()
        editor.putInt("havePoint", saveNum).apply()
    }

}