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
        if(saveNum > 9999){
            editor.putInt("havePoint", 9999).apply()
        }else {
            editor.putInt("havePoint", saveNum).apply()
        }
    }

}