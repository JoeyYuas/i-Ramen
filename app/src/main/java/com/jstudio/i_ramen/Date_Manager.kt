package com.jstudio.i_ramen

import java.text.SimpleDateFormat
import java.util.*

class Date_Manager {

    //日付をInt型のListとして返す
    fun getIntDate(): Array<Int> {

        val date = Date()
        val nD = SimpleDateFormat("dd")
        var nowDay = nD.format(date).toInt()
        val nM = SimpleDateFormat("MM")
        var nowMonth = nM.format(date).toInt()
        val nY = SimpleDateFormat("yyyy")
        var nowYear = nY.format(date).toInt()

        if (nowMonth != 1) {
            nowMonth -= 1
        }
        return arrayOf(nowYear, nowMonth, nowDay)
    }

}