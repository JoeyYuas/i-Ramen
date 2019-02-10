package com.jstudio.i_ramen
import android.content.SharedPreferences

class PointManager {

    fun getPoint(SharedPreferences: SharedPreferences): Int{
        var havePoint = SharedPreferences.getInt("havePoint", 100)

//        ポイントを0以下にさせない処理
        if(havePoint < 0){
            havePoint = 0
            setPoint(SharedPreferences, havePoint)
        }

        return havePoint
    }

    fun setPoint(SharedPreferences: SharedPreferences, saveNum:Int){
        val editor = SharedPreferences.edit()
        editor.putInt("havePoint", saveNum).apply()
    }

}