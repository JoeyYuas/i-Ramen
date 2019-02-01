package com.jstudio.i_ramen

import android.app.Activity
import java.io.BufferedReader
import java.io.InputStreamReader


class ReadText {

    fun getTextOneLine(activity: Activity):String {
        val inputStream = activity.assets.open("quest-list.txt")
        val bufferReader = BufferedReader(InputStreamReader(inputStream))

        val readRandomText = bufferReader.readLines()

        var lineCount = 0

        for (outText in readRandomText){
            lineCount ++
        }

//        ０からプラスしていくため、余分に１つ多くカウントされてしまう
        lineCount -= 1

        val random: Int = (0..lineCount).random()

        return readRandomText[random]


    }

}