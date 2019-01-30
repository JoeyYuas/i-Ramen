package com.jstudio.i_ramen

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*


class DateManager {

    //日付をInt型のListとして返す
    fun getIntDate(): Array<Int> {

        val date = Date()
        val nD = SimpleDateFormat("dd")
        val nowDay = nD.format(date).toInt()
        val nM = SimpleDateFormat("MM")
        val nowMonth = if (nM.format(date).toInt() != 1) nM.format(date).toInt() - 1 else nM.format(date).toInt()
        val nY = SimpleDateFormat("yyyy")
        val nowYear = nY.format(date).toInt()

        return arrayOf(nowYear, nowMonth, nowDay)
    }

//    前回起動時から一日経過しているかどうかの判定
    fun isNextDay(SharedPreferences: SharedPreferences): Boolean {
        var checkNext = false
        val dateList = getIntDate()
        val today = dateList[2]

        val editor = SharedPreferences.edit()
        val yesterday = SharedPreferences.getInt("yesterday", 0)

        if (today != yesterday) {
            editor.putInt("yesterday", today)
            editor.apply()
            checkNext = true
        }
        return checkNext
    }

//    一か月が経過しているかどうかの判定
    fun isNextMonth(SharedPreferences: SharedPreferences): Boolean {
        var checkNext = false
        val dateList = getIntDate()
        val thisMonth = dateList[1]

        val editor = SharedPreferences.edit()
        val lastMonth = SharedPreferences.getInt("lastMonth", 0)

        if (thisMonth != lastMonth) {
            editor.putInt("lastMonth", thisMonth)
            editor.apply()
            checkNext = true
        }
        return checkNext
    }
}